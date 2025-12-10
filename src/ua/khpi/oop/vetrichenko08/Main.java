package ua.khpi.oop.vetrichenko08;

import ua.khpi.oop.vetrichenko07.AddressEntry; // Імпортуємо клас з минулої лаби

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Лабораторна робота №8.
 * Тема: Основи введення/виведення. Long Term Persistence.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    // Використовуємо масив (або список) для зберігання
    private static AddressEntry[] book = new AddressEntry[10];
    private static int count = 0;

    public static void main(String[] args) {
        // Налаштування консолі на UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        boolean running = true;
        while (running) {
            System.out.println("\n=== МЕНЮ (LAB 8: XML Persistence) ===");
            System.out.println("1. Додати контакт");
            System.out.println("2. Показати контакти");
            System.out.println("3. Зберегти у XML (з вибором папки)");
            System.out.println("4. Завантажити з XML (з вибором папки)");
            System.out.println("5. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEntry(scanner);
                    break;
                case "2":
                    showEntries();
                    break;
                case "3":
                    saveToXML(scanner);
                    break;
                case "4":
                    loadFromXML(scanner);
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Невідома команда.");
            }
        }
    }

    // --- Логіка роботи з даними ---

    private static void addEntry(Scanner scanner) {
        if (count >= book.length) {
            System.out.println("Масив переповнений!");
            return;
        }
        AddressEntry entry = new AddressEntry();
        System.out.print("ПІБ: ");
        entry.setFullName(scanner.nextLine());
        System.out.print("Дата народження: ");
        entry.setBirthDate(scanner.nextLine());
        System.out.print("Адреса: ");
        entry.setAddress(scanner.nextLine());
        System.out.print("Телефон: ");
        entry.addPhone(scanner.nextLine());

        book[count++] = entry;
        System.out.println("Додано.");
    }

    private static void showEntries() {
        if (count == 0) System.out.println("Список порожній.");
        for (int i = 0; i < count; i++) {
            System.out.println(book[i]);
        }
    }

    // --- Логіка Long Term Persistence (XML) ---

    private static void saveToXML(Scanner scanner) {
        File directory = chooseDirectory(scanner); // Вибір папки
        if (directory == null) return;

        System.out.print("Введіть назву файлу (напр. data.xml): ");
        String filename = scanner.nextLine();
        File file = new File(directory, filename);

        // Використання XMLEncoder
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            // Зберігаємо кількість записів
            encoder.writeObject(count);
            // Зберігаємо сам масив
            encoder.writeObject(book);
            System.out.println("Успішно збережено у " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.err.println("Помилка доступу до файлу: " + e.getMessage());
        }
    }

    private static void loadFromXML(Scanner scanner) {
        File directory = chooseDirectory(scanner);
        if (directory == null) return;

        System.out.print("Введіть назву файлу для завантаження: ");
        String filename = scanner.nextLine();
        File file = new File(directory, filename);

        if (!file.exists()) {
            System.out.println("Файл не знайдено!");
            return;
        }

        // Використання XMLDecoder
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Integer loadedCount = (Integer) decoder.readObject();
            AddressEntry[] loadedBook = (AddressEntry[]) decoder.readObject();

            count = loadedCount;
            book = loadedBook;
            System.out.println("Дані завантажено! Кількість записів: " + count);
        } catch (FileNotFoundException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    // --- Логіка Навігації по файловій системі ---

    private static File chooseDirectory(Scanner scanner) {
        File currentDir = new File(System.getProperty("user.dir")); // Поточна папка проекту

        while (true) {
            System.out.println("\n--- Навігація ---");
            System.out.println("Поточна папка: " + currentDir.getAbsolutePath());
            System.out.println("Вміст:");

            File[] files = currentDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) System.out.println(" [DIR]  " + f.getName());
                }
            }

            System.out.println("\nКоманди:");
            System.out.println(" - Введіть ім'я папки, щоб увійти");
            System.out.println(" - '..' щоб вийти на рівень вгору");
            System.out.println(" - 'ok' щоб вибрати цю папку");
            System.out.println(" - 'cancel' для скасування");
            System.out.print("-> ");

            String input = scanner.nextLine();

            if (input.equals("ok")) {
                return currentDir;
            } else if (input.equals("cancel")) {
                return null;
            } else if (input.equals("..")) {
                File parent = currentDir.getParentFile();
                if (parent != null) currentDir = parent;
            } else {
                File newDir = new File(currentDir, input);
                if (newDir.exists() && newDir.isDirectory()) {
                    currentDir = newDir;
                } else {
                    System.out.println("Помилка: Папку не знайдено або це не папка.");
                }
            }
        }
    }
}