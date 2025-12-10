package ua.khpi.oop.vetrichenko14;

import ua.khpi.oop.vetrichenko07.AddressEntry;
import ua.khpi.oop.vetrichenko10.LinkedContainer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Лабораторна робота №14.
 * Тема: Ефективність мультипоточності.
 * Порівняння Sequential vs Parallel виконання.
 */
public class Main {

    // Кількість елементів (зробимо менше, ніж в минулій, але більшу затримку)
    private static final int DATA_SIZE = 1000;

    // Штучна затримка на кожному елементі (мілісекунди)
    // 1000 елементів * 2 мс = 2 секунди на одну задачу в послідовному режимі
    private static final int DELAY = 2;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Lab 14: Parallel vs Sequential ===");

        // 1. Генерація даних
        System.out.print("Генерація " + DATA_SIZE + " записів... ");
        LinkedContainer<AddressEntry> container = generateData(DATA_SIZE);
        System.out.println("Готово.\n");

        System.out.println("Затримка емуляції: " + DELAY + " мс на елемент.");
        System.out.println("Кількість задач: 3\n");

        // 2. Послідовне виконання (Sequential)
        System.out.println("--- Запуск ПОСЛІДОВНОЇ обробки ---");
        long startSeq = System.currentTimeMillis();

        taskCountVodafone(container);
        taskCountKyiv(container);
        taskFindOldest(container);

        long endSeq = System.currentTimeMillis();
        long timeSeq = endSeq - startSeq;
        System.out.println("Час послідовного виконання: " + timeSeq + " мс\n");

        // 3. Паралельне виконання (Parallel)
        System.out.println("--- Запуск ПАРАЛЕЛЬНОЇ обробки ---");
        long startPar = System.currentTimeMillis();

        Thread t1 = new Thread(() -> taskCountVodafone(container));
        Thread t2 = new Thread(() -> taskCountKyiv(container));
        Thread t3 = new Thread(() -> taskFindOldest(container));

        t1.start();
        t2.start();
        t3.start();

        // Чекаємо завершення всіх потоків
        t1.join();
        t2.join();
        t3.join();

        long endPar = System.currentTimeMillis();
        long timePar = endPar - startPar;
        System.out.println("Час паралельного виконання: " + timePar + " мс\n");

        // 4. Висновки та таблиця
        printReport(timeSeq, timePar);
    }

    // --- Задачі (Logic) ---

    private static void taskCountVodafone(LinkedContainer<AddressEntry> container) {
        int count = 0;
        for (AddressEntry entry : container) {
            simulateWork(); // Штучна затримка
            for (String phone : entry.getPhones()) {
                if (phone.matches(".*(050|066|095|099).*")) {
                    count++;
                    break;
                }
            }
        }
        // System.out.println("Vodafone: " + count); // Закоментуємо, щоб не засмічувати консоль
    }

    private static void taskCountKyiv(LinkedContainer<AddressEntry> container) {
        int count = 0;
        for (AddressEntry entry : container) {
            simulateWork();
            if (entry.getAddress().contains("Київ")) {
                count++;
            }
        }
        // System.out.println("Киян: " + count);
    }

    private static void taskFindOldest(LinkedContainer<AddressEntry> container) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate minDate = LocalDate.now();
        for (AddressEntry entry : container) {
            simulateWork();
            LocalDate current = LocalDate.parse(entry.getBirthDate(), dtf);
            if (current.isBefore(minDate)) {
                minDate = current;
            }
        }
        // System.out.println("Найстарший народився: " + minDate);
    }

    /**
     * Метод для емуляції важких обчислень.
     */
    private static void simulateWork() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void printReport(long seq, long par) {
        System.out.println("==========================================");
        System.out.println("            РЕЗУЛЬТАТИ ТЕСТУВАННЯ         ");
        System.out.println("==========================================");
        System.out.printf("| %-20s | %-15s |%n", "Режим", "Час (мс)");
        System.out.println("|----------------------|-----------------|");
        System.out.printf("| %-20s | %-15d |%n", "Послідовно", seq);
        System.out.printf("| %-20s | %-15d |%n", "Паралельно", par);
        System.out.println("==========================================");

        double speedup = (double) seq / par;
        System.out.printf("Прискорення: %.2f разів%n", speedup);
    }

    // Генератор даних (той самий)
    private static LinkedContainer<AddressEntry> generateData(int count) {
        LinkedContainer<AddressEntry> temp = new LinkedContainer<>();
        Random random = new Random();
        String[] cities = {"Київ", "Харків", "Львів", "Одеса", "Дніпро"};
        for (int i = 0; i < count; i++) {
            String city = cities[random.nextInt(cities.length)];
            int year = 1950 + random.nextInt(50);
            int month = 1 + random.nextInt(12);
            int day = 1 + random.nextInt(28);
            String date = String.format("%02d.%02d.%d", day, month, year);
            AddressEntry entry = new AddressEntry("User " + i, date, city);
            entry.addPhone("050" + (1000000 + random.nextInt(8999999)));
            temp.add(entry);
        }
        return temp;
    }
}