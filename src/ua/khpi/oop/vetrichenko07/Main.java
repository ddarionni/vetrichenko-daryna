package ua.khpi.oop.vetrichenko07;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Лабораторна робота №7.
 * Тема: Об'єктно-орієнтована декомпозиція.
 * Варіант 3: Адресна книга.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        // Налаштування кирилиці для консолі
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        // Масив об'єктів (спрощений контейнер)
        AddressEntry[] book = new AddressEntry[10];
        int count = 0;

        boolean running = true;
        while (running) {
            System.out.println("\n=== АДРЕСНА КНИГА (LAB 7) ===");
            System.out.println("1. Додати новий контакт");
            System.out.println("2. Показати всі контакти");
            System.out.println("3. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (count >= book.length) {
                        System.out.println("Книга переповнена!");
                        break;
                    }
                    AddressEntry entry = new AddressEntry();

                    System.out.print("Введіть ПІБ: ");
                    entry.setFullName(scanner.nextLine());

                    System.out.print("Введіть дату народження (DD.MM.YYYY): ");
                    entry.setBirthDate(scanner.nextLine());

                    System.out.print("Введіть адресу: ");
                    entry.setAddress(scanner.nextLine());

                    System.out.println("Введення телефонів (введіть 'end' для завершення):");
                    while (true) {
                        System.out.print("-> ");
                        String phone = scanner.nextLine();
                        if (phone.equalsIgnoreCase("end")) break;
                        entry.addPhone(phone);
                    }

                    book[count++] = entry;
                    System.out.println("Контакт успішно додано!");
                    break;

                case "2":
                    if (count == 0) {
                        System.out.println("Книга порожня.");
                    } else {
                        for (int i = 0; i < count; i++) {
                            System.out.println(book[i].toString());
                        }
                    }
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Невідома команда.");
            }
        }
    }
}
