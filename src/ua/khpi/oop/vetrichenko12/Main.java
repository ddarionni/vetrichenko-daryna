package ua.khpi.oop.vetrichenko12;

import ua.khpi.oop.vetrichenko07.AddressEntry;
import ua.khpi.oop.vetrichenko10.LinkedContainer;

import java.util.Scanner;

/**
 * Лабораторна робота №12.
 * Тема: Обробка тексту регулярними виразами.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-auto")) {
            autoMode();
        } else {
            interactiveMode();
        }
    }

    private static void autoMode() {
        System.out.println("=== АВТОМАТИЧНИЙ РЕЖИМ ===");
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        // 1. Користувач не підходить (Тільки Life)
        AddressEntry user1 = new AddressEntry("Бойко Андрій", "01.01.1990", "Київ");
        user1.addPhone("0631234567"); // Life
        container.add(user1);

        // 2. Користувач не підходить (Life + KS, немає міського)
        AddressEntry user2 = new AddressEntry("Шевчук Олена", "02.02.1995", "Харків");
        user2.addPhone("0931112233"); // Life
        user2.addPhone("0679998877"); // KS
        container.add(user2);

        // 3. ІДЕАЛЬНИЙ КОРИСТУВАЧ (Всі три оператори)
        AddressEntry user3 = new AddressEntry("Харківський Мультиабонент", "05.05.2000", "Харків");
        user3.addPhone("0577001234"); // Харків міський
        user3.addPhone("0635556677"); // Lifecell
        user3.addPhone("0971234567"); // Kyivstar
        user3.addPhone("0501111111"); // Vodafone (зайвий, не заважає)
        container.add(user3);

        System.out.println("Всього контактів: " + container.size());

        // Запуск пошуку
        PhoneSearcher.printMatchingContacts(container);
    }

    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        System.out.println("=== ІНТЕРАКТИВНИЙ РЕЖИМ ===");

        // Для тесту в діалоговому режимі додамо тих самих користувачів
        // Або можна реалізувати повне меню введення (як в лаб 10)
        // Для скорочення коду тут продублюємо логіку авто, але з підтвердженням

        System.out.println("Генерую тестові дані...");
        AddressEntry ideal = new AddressEntry("Тестовий Користувач", "01.01.2000", "Тест");
        ideal.addPhone("0571111111");
        ideal.addPhone("0632222222");
        ideal.addPhone("0963333333");
        container.add(ideal);

        while (true) {
            System.out.println("\n1. Показати всіх");
            System.out.println("2. Знайти абонентів (Kharkiv + Life + KS)");
            System.out.println("3. Вихід");
            System.out.print("-> ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println(container);
            } else if (choice.equals("2")) {
                PhoneSearcher.printMatchingContacts(container);
            } else if (choice.equals("3")) {
                break;
            }
        }
    }
}