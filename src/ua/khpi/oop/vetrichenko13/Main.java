package ua.khpi.oop.vetrichenko13;

import ua.khpi.oop.vetrichenko07.AddressEntry;
import ua.khpi.oop.vetrichenko10.LinkedContainer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * Лабораторна робота №13.
 * Тема: Паралельне виконання. Multithreading.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 */
public class Main {

    public static void main(String[] args) {
        // 1. Генерація великого набору даних
        System.out.println("Генерація даних (це може зайняти час)...");
        LinkedContainer<AddressEntry> container = generateData(5000);
        System.out.println("Згенеровано записів: " + container.size());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть таймаут виконання (мілісекунди, напр. 1000): ");
        long timeout = scanner.nextLong();

        System.out.println("\n--- ЗАПУСК ПОТОКІВ ---");

        // 2. Створення задач (Runnable)
        // Завдання 1: Рахуємо абонентів Vodafone (050, 066, 095, 099)
        Runnable task1 = () -> {
            int count = 0;
            System.out.println("[Thread-1] Почав рахувати Vodafone...");
            try {
                for (AddressEntry entry : container) {
                    // Перевірка на переривання (обов'язково!)
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("[Thread-1] ПЕРЕРВАНО!");
                        return;
                    }

                    // Імітація важкої роботи (щоб встигнути побачити роботу таймаута)
                    Thread.sleep(1);

                    for (String phone : entry.getPhones()) {
                        if (phone.matches(".*(050|066|095|099).*")) {
                            count++;
                            break;
                        }
                    }
                }
                System.out.println("[Thread-1] Завершено. Vodafone: " + count);
            } catch (InterruptedException e) {
                System.out.println("[Thread-1] ПЕРЕРВАНО (під час сну)!");
            }
        };

        // Завдання 2: Рахуємо киян
        Runnable task2 = () -> {
            int count = 0;
            System.out.println("[Thread-2] Почав рахувати киян...");
            try {
                for (AddressEntry entry : container) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("[Thread-2] ПЕРЕРВАНО!");
                        return;
                    }
                    Thread.sleep(1); // Імітація затримки

                    if (entry.getAddress().contains("Київ")) {
                        count++;
                    }
                }
                System.out.println("[Thread-2] Завершено. Киян: " + count);
            } catch (InterruptedException e) {
                System.out.println("[Thread-2] ПЕРЕРВАНО!");
            }
        };

        // Завдання 3: Шукаємо найстаршу людину (мінімальна дата народження)
        Runnable task3 = () -> {
            System.out.println("[Thread-3] Почав шукати найстаршого...");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate minDate = LocalDate.now();
            String oldestName = "";

            try {
                for (AddressEntry entry : container) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("[Thread-3] ПЕРЕРВАНО!");
                        return;
                    }
                    Thread.sleep(1);

                    LocalDate current = LocalDate.parse(entry.getBirthDate(), dtf);
                    if (current.isBefore(minDate)) {
                        minDate = current;
                        oldestName = entry.getFullName();
                    }
                }
                System.out.println("[Thread-3] Завершено. Найстарший: " + oldestName + " (" + minDate + ")");
            } catch (InterruptedException e) {
                System.out.println("[Thread-3] ПЕРЕРВАНО!");
            }
        };

        // 3. Запуск потоків
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        Thread t3 = new Thread(task3);

        long startTime = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();

        // 4. Моніторинг часу (Головний потік чекає)
        try {
            // Чекаємо завершення потоків не більше ніж timeout
            t1.join(timeout);
            // Якщо після join потік все ще живий, значить час вийшов
            if (t1.isAlive()) {
                t1.interrupt();
            }

            // Розраховуємо залишок часу для інших потоків
            long elapsed = System.currentTimeMillis() - startTime;
            long timeLeft = Math.max(0, timeout - elapsed);

            t2.join(timeLeft);
            if (t2.isAlive()) t2.interrupt();

            elapsed = System.currentTimeMillis() - startTime;
            timeLeft = Math.max(0, timeout - elapsed);

            t3.join(timeLeft);
            if (t3.isAlive()) t3.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--- РОБОТУ ЗАВЕРШЕНО ---");
    }

    /**
     * Допоміжний метод для генерації випадкових даних.
     */
    private static LinkedContainer<AddressEntry> generateData(int count) {
        LinkedContainer<AddressEntry> temp = new LinkedContainer<>();
        Random random = new Random();
        String[] cities = {"Київ", "Харків", "Львів", "Одеса", "Дніпро"};
        String[] names = {"Іваненко", "Петренко", "Сидоренко", "Коваленко", "Бондаренко"};

        for (int i = 0; i < count; i++) {
            String name = names[random.nextInt(names.length)] + " " + i;
            String city = cities[random.nextInt(cities.length)];

            // Випадкова дата 1950-2000
            int year = 1950 + random.nextInt(50);
            int month = 1 + random.nextInt(12);
            int day = 1 + random.nextInt(28);
            String date = String.format("%02d.%02d.%d", day, month, year);

            AddressEntry entry = new AddressEntry(name, date, city);

            // Випадковий телефон
            String code = random.nextBoolean() ? "050" : "067"; // 50/50 Vodafone або KS
            entry.addPhone(code + (1000000 + random.nextInt(8999999)));

            temp.add(entry);
        }
        return temp;
    }
}
