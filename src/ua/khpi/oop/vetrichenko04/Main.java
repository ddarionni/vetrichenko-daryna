package ua.khpi.oop.vetrichenko04;

import java.util.Scanner;

/**
 * Лабораторна робота №4.
 * Тема: Інтерактивні консольні програми.
 * <p>
 * Підтримує ключі командного рядка:
 * -h або -help : вивід довідки
 * -d або -debug : режим налагодження
 * </p>
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    /**
     * Прапор режиму налагодження.
     */
    private static boolean debugMode = false;

    /**
     * Поточні дані (масив рядків).
     */
    private static String[] data = null;

    /**
     * Точка входу в програму.
     * Аналізує аргументи та запускає головне меню.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        // 1. Обробка аргументів командного рядка
        for (String arg : args) {
            if (arg.equals("-h") || arg.equals("-help")) {
                printHelp();
                return; // Завершуємо роботу після виводу довідки
            }
            if (arg.equals("-d") || arg.equals("-debug")) {
                debugMode = true;
                System.out.println("[DEBUG] Режим налагодження увімкнено.");
            }
        }

        if (debugMode) {
            System.out.println("[DEBUG] Запуск методу main(). Аргументів отримано: " + args.length);
        }

        // 2. Запуск інтерактивного меню
        runMenu();
    }

    /**
     * Основний цикл програми (меню).
     */
    private static void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n================ МЕНЮ ================");
            System.out.println("1. Ввести дані (створити масив рядків)");
            System.out.println("2. Переглянути поточні дані");
            System.out.println("3. Виконати обчислення (Варіант 3)");
            System.out.println("4. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    inputData(scanner);
                    break;
                case "2":
                    viewData();
                    break;
                case "3":
                    calculate();
                    break;
                case "4":
                    running = false;
                    System.out.println("Роботу завершено.");
                    break;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    /**
     * Введення даних користувачем.
     */
    private static void inputData(Scanner scanner) {
        if (debugMode) System.out.println("[DEBUG] Виклик inputData()...");

        System.out.print("Введіть кількість рядків: ");
        int count = 0;
        try {
            count = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Помилка! Введіть ціле число.");
            return;
        }

        data = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Рядок " + (i + 1) + ": ");
            data[i] = scanner.nextLine();
        }

        if (debugMode) System.out.println("[DEBUG] Масив ініціалізовано. Розмір: " + data.length);
        System.out.println("Дані успішно збережено.");
    }

    /**
     * Відображення поточних даних.
     */
    private static void viewData() {
        if (data == null || data.length == 0) {
            System.out.println("Дані відсутні. Спочатку оберіть пункт 1.");
            return;
        }

        System.out.println("\n--- Поточні рядки ---");
        for (int i = 0; i < data.length; i++) {
            System.out.println((i + 1) + ". " + data[i] + " [length: " + data[i].length() + "]");
        }
    }

    /**
     * Виконання розрахунків (Варіант 3).
     */
    private static void calculate() {
        if (debugMode) System.out.println("[DEBUG] Виклик calculate()...");

        if (data == null || data.length == 0) {
            System.out.println("Дані відсутні. Спочатку оберіть пункт 1.");
            return;
        }

        // Використання StringHelper з Лабораторної №3
        double average = StringHelper.calculateAverageLength(data);

        if (debugMode) System.out.println("[DEBUG] Середня довжина обчислена: " + average);

        System.out.printf("\nСередня довжина: %.2f%n", average);

        String reportLess = StringHelper.getGroupReport(data, average, true);
        System.out.println(reportLess);

        String reportMore = StringHelper.getGroupReport(data, average, false);
        System.out.println(reportMore);
    }

    /**
     * Виводить довідку про програму (-h).
     */
    private static void printHelp() {
        System.out.println("================ HELP ================");
        System.out.println("Автор: Ветріченко Дарина, група КН-924з");
        System.out.println("Завдання (Вар. 3): Розбити рядки на дві групи відносно середньої довжини.");
        System.out.println("\nКлючі запуску:");
        System.out.println(" -h, -help  : Показати цю довідку");
        System.out.println(" -d, -debug : Увімкнути режим налагодження (додаткові повідомлення)");
        System.out.println("======================================");
    }
}