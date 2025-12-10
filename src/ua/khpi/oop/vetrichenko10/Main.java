package ua.khpi.oop.vetrichenko10;

import ua.khpi.oop.vetrichenko07.AddressEntry; // Імпорт або копія в поточному пакеті

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 * Лабораторна робота №10.
 * Тема: Розширення функціональності параметризованих класів.
 * Варіант 3: Сортування адресної книги.
 */
public class Main {

    // --- Компаратори (Правила сортування) ---

    // 1. Сортування за Прізвищем (вважаємо, що fullName = "Прізвище Ім'я")
    private static final Comparator<AddressEntry> BY_SURNAME = (o1, o2) -> {
        String s1 = o1.getFullName().split(" ")[0];
        String s2 = o2.getFullName().split(" ")[0];
        return s1.compareToIgnoreCase(s2);
    };

    // 2. Сортування за Ім'ям
    private static final Comparator<AddressEntry> BY_NAME = (o1, o2) -> {
        String[] parts1 = o1.getFullName().split(" ");
        String[] parts2 = o2.getFullName().split(" ");
        // Якщо імені немає, беремо порожній рядок
        String n1 = parts1.length > 1 ? parts1[1] : "";
        String n2 = parts2.length > 1 ? parts2[1] : "";
        return n1.compareToIgnoreCase(n2);
    };

    // 3. Сортування за Датою народження (парсинг рядка DD.MM.YYYY)
    private static final Comparator<AddressEntry> BY_BIRTH_DATE = (o1, o2) -> {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate d1 = LocalDate.parse(o1.getBirthDate(), dtf);
        LocalDate d2 = LocalDate.parse(o2.getBirthDate(), dtf);
        return d1.compareTo(d2);
    };

    // 4. Сортування за Датою редагування (LocalDateTime)
    private static final Comparator<AddressEntry> BY_EDIT_TIME = (o1, o2) ->
            o1.getLastEditTime().compareTo(o2.getLastEditTime());


    public static void main(String[] args) {
        // Перевірка режиму запуску
        if (args.length > 0 && args[0].equals("-auto")) {
            autoMode();
        } else {
            interactiveMode();
        }
    }

    /**
     * Автоматичний режим (без участі користувача).
     */
    private static void autoMode() {
        System.out.println("=== ЗАПУСК В АВТОМАТИЧНОМУ РЕЖИМІ (-auto) ===");

        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        // Генерація даних
        System.out.println("1. Генерація тестових даних...");
        container.add(new AddressEntry("Шевченко Тарас", "09.03.1814", "Канів"));
        container.add(new AddressEntry("Франко Іван", "27.08.1856", "Львів"));
        container.add(new AddressEntry("Леся Українка", "25.02.1871", "Звягель"));
        container.add(new AddressEntry("Котляревський Іван", "09.09.1769", "Полтава"));
        container.add(new AddressEntry("Костенко Ліна", "19.03.1930", "Київ"));

        System.out.println("\n--- Початковий список ---");
        System.out.println(container);

        // Демонстрація сортувань
        System.out.println("--- Сортування за Прізвищем ---");
        ContainerTools.sort(container, BY_SURNAME);
        printShort(container);

        System.out.println("\n--- Сортування за Ім'ям ---");
        ContainerTools.sort(container, BY_NAME);
        printShort(container);

        System.out.println("\n--- Сортування за Датою народження ---");
        ContainerTools.sort(container, BY_BIRTH_DATE);
        printShort(container);

        System.out.println("=== Автоматичний режим завершено ===");
    }

    /**
     * Інтерактивний режим (Меню).
     */
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        boolean running = true;
        while (running) {
            System.out.println("\n=== МЕНЮ (LAB 10) ===");
            System.out.println("1. Додати контакт");
            System.out.println("2. Показати всі");
            System.out.println("3. Сортувати за Прізвищем");
            System.out.println("4. Сортувати за Ім'ям");
            System.out.println("5. Сортувати за Датою народження");
            System.out.println("6. Сортувати за Часом редагування");
            System.out.println("7. Вихід");
            System.out.print("-> ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    AddressEntry entry = new AddressEntry();
                    System.out.print("Введіть Прізвище та Ім'я: ");
                    entry.setFullName(scanner.nextLine());
                    System.out.print("Дата народження (dd.MM.yyyy): ");
                    entry.setBirthDate(scanner.nextLine());
                    System.out.print("Адреса: ");
                    entry.setAddress(scanner.nextLine());
                    container.add(entry);
                    break;
                case "2":
                    System.out.println(container);
                    break;
                case "3":
                    ContainerTools.sort(container, BY_SURNAME);
                    System.out.println("Відсортовано!");
                    break;
                case "4":
                    ContainerTools.sort(container, BY_NAME);
                    System.out.println("Відсортовано!");
                    break;
                case "5":
                    ContainerTools.sort(container, BY_BIRTH_DATE);
                    System.out.println("Відсортовано!");
                    break;
                case "6":
                    ContainerTools.sort(container, BY_EDIT_TIME);
                    System.out.println("Відсортовано!");
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Невірна команда.");
            }
        }
    }

    // Допоміжний метод для короткого виводу в авто-режимі
    private static void printShort(LinkedContainer<AddressEntry> c) {
        for (AddressEntry a : c) {
            System.out.println(a.getFullName() + " (" + a.getBirthDate() + ")");
        }
    }
}