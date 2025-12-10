package ua.khpi.oop.vetrichenko11;

import ua.khpi.oop.vetrichenko07.AddressEntry;
import ua.khpi.oop.vetrichenko10.LinkedContainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Лабораторна робота №11.
 * Тема: Регулярні вирази. Валідація даних.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-auto")) {
            autoMode();
        } else {
            interactiveMode();
        }
    }

    /**
     * Автоматичний режим: Читання з файлу та валідація.
     */
    private static void autoMode() {
        System.out.println("=== АВТО-РЕЖИМ: Імпорт з файлу ===");
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();
        File file = new File("import_data.txt");

        if (!file.exists()) {
            System.err.println("Файл import_data.txt не знайдено! Створіть його в папці проекту.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                // Очікуваний формат файлу: ПІБ;Дата;Адреса;Телефон
                String[] parts = line.split(";");

                if (parts.length < 4) {
                    System.out.println("Рядок " + lineNum + ": [ERROR] Недостатньо даних.");
                    continue;
                }

                String name = parts[0].trim();
                String date = parts[1].trim();
                String address = parts[2].trim();
                String phone = parts[3].trim();

                // ВАЛІДАЦІЯ
                boolean valid = true;
                if (!RegexValidator.isValidName(name)) {
                    System.out.println("Рядок " + lineNum + ": [INVALID] Невірне ім'я: " + name);
                    valid = false;
                }
                if (!RegexValidator.isValidDate(date)) {
                    System.out.println("Рядок " + lineNum + ": [INVALID] Невірна дата: " + date);
                    valid = false;
                }
                if (!RegexValidator.isValidAddress(address)) {
                    System.out.println("Рядок " + lineNum + ": [INVALID] Коротка адреса: " + address);
                    valid = false;
                }
                if (!RegexValidator.isValidPhone(phone)) {
                    System.out.println("Рядок " + lineNum + ": [INVALID] Невірний телефон: " + phone);
                    valid = false;
                }

                if (valid) {
                    AddressEntry entry = new AddressEntry(name, date, address);
                    entry.addPhone(phone);
                    container.add(entry);
                    System.out.println("Рядок " + lineNum + ": [OK] Додано: " + name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Результат імпорту ---");
        System.out.println("Всього записів: " + container.size());
        System.out.println(container);
    }

    /**
     * Інтерактивний режим: Введення з перевіркою.
     */
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        System.out.println("=== ДІАЛОГОВИЙ РЕЖИМ: Введення з валідацією ===");

        while (true) {
            System.out.println("\n1. Додати контакт");
            System.out.println("2. Показати всі");
            System.out.println("3. Вихід");
            System.out.print("-> ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                String name, date, address, phone;

                // Цикл запиту, поки не введемо вірно
                while (true) {
                    System.out.print("Введіть ПІБ (Напр: Шевченко Тарас): ");
                    name = scanner.nextLine();
                    if (RegexValidator.isValidName(name)) break;
                    System.err.println("Помилка! Має бути два слова з великої літери.");
                }

                while (true) {
                    System.out.print("Введіть Дату (DD.MM.YYYY): ");
                    date = scanner.nextLine();
                    if (RegexValidator.isValidDate(date)) break;
                    System.err.println("Помилка! Формат має бути DD.MM.YYYY");
                }

                while (true) {
                    System.out.print("Введіть Адресу (мін. 3 символи): ");
                    address = scanner.nextLine();
                    if (RegexValidator.isValidAddress(address)) break;
                    System.err.println("Помилка! Адреса надто коротка.");
                }

                AddressEntry entry = new AddressEntry(name, date, address);

                while (true) {
                    System.out.print("Введіть Телефон (+380... або 0...): ");
                    phone = scanner.nextLine();
                    if (RegexValidator.isValidPhone(phone)) {
                        entry.addPhone(phone);
                        break;
                    }
                    System.err.println("Помилка! Невірний формат номеру.");
                }

                container.add(entry);
                System.out.println("Контакт успішно додано!");

            } else if (choice.equals("2")) {
                System.out.println(container);
            } else if (choice.equals("3")) {
                break;
            }
        }
    }
}