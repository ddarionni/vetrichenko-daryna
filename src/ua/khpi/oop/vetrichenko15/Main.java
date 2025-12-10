package ua.khpi.oop.vetrichenko15;

import ua.khpi.oop.vetrichenko07.AddressEntry;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Лабораторна робота №15.
 * Тема: Колекції в Java (Java Collections Framework).
 * Використання ArrayList замість власного контейнера.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 */
public class Main {

    // --- Компаратори (Правила сортування з Лаб 10) ---
    private static final Comparator<AddressEntry> BY_SURNAME = (o1, o2) -> {
        String s1 = o1.getFullName().split(" ")[0];
        String s2 = o2.getFullName().split(" ")[0];
        return s1.compareToIgnoreCase(s2);
    };

    private static final Comparator<AddressEntry> BY_NAME = (o1, o2) -> {
        String[] p1 = o1.getFullName().split(" ");
        String[] p2 = o2.getFullName().split(" ");
        String n1 = p1.length > 1 ? p1[1] : "";
        String n2 = p2.length > 1 ? p2[1] : "";
        return n1.compareToIgnoreCase(n2);
    };

    private static final Comparator<AddressEntry> BY_BIRTH = (o1, o2) -> {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(o1.getBirthDate(), dtf)
                .compareTo(LocalDate.parse(o2.getBirthDate(), dtf));
    };

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-auto")) {
            autoMode();
        } else {
            interactiveMode();
        }
    }

    /**
     * Автоматичний режим: Генерація, Сортування, Збереження.
     */
    private static void autoMode() {
        System.out.println("=== АВТОМАТИЧНИЙ РЕЖИМ (ArrayList) ===");

        // 1. Створення стандартної колекції
        List<AddressEntry> list = new ArrayList<>();

        // 2. Заповнення
        list.add(new AddressEntry("Яковенко Петро", "01.01.1990", "Київ"));
        list.add(new AddressEntry("Андрієнко Андрій", "05.05.1985", "Львів"));
        list.add(new AddressEntry("Шевченко Тарас", "09.03.1814", "Канів"));

        System.out.println("\n--- Початковий список (" + list.size() + ") ---");
        list.forEach(System.out::println); // Method Reference (Java 8+)

        // 3. Сортування (стандартний метод List)
        System.out.println("\n--- Сортування за Прізвищем ---");
        list.sort(BY_SURNAME);
        list.forEach(a -> System.out.println(a.getFullName()));

        System.out.println("\n--- Сортування за Датою народження ---");
        list.sort(BY_BIRTH);
        list.forEach(a -> System.out.println(a.getFullName() + " - " + a.getBirthDate()));

        // 4. Збереження
        saveToXML(list, "lab15_auto.xml");
        System.out.println("\nДані збережено у XML.");
    }

    /**
     * Інтерактивний режим.
     */
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        List<AddressEntry> list = new ArrayList<>(); // Використовуємо ArrayList

        boolean running = true;
        while (running) {
            System.out.println("\n=== МЕНЮ (LAB 15: JCF) ===");
            System.out.println("1. Додати");
            System.out.println("2. Показати всі");
            System.out.println("3. Сортувати (Прізвище/Ім'я/Дата)");
            System.out.println("4. Пошук (за фрагментом прізвища)");
            System.out.println("5. Видалити (за індексом)");
            System.out.println("6. Зберегти/Завантажити (XML)");
            System.out.println("7. Вихід");
            System.out.print("-> ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    AddressEntry entry = new AddressEntry();
                    System.out.print("ПІБ: "); entry.setFullName(scanner.nextLine());
                    System.out.print("Дата (dd.mm.yyyy): "); entry.setBirthDate(scanner.nextLine());
                    System.out.print("Адреса: "); entry.setAddress(scanner.nextLine());
                    list.add(entry); // Стандартний метод add
                    System.out.println("Додано!");
                    break;
                case "2":
                    if (list.isEmpty()) System.out.println("Список порожній.");
                    else list.forEach(System.out::println);
                    break;
                case "3":
                    System.out.println("1-Прізвище, 2-Ім'я, 3-Дата");
                    String sType = scanner.nextLine();
                    if (sType.equals("1")) list.sort(BY_SURNAME);
                    if (sType.equals("2")) list.sort(BY_NAME);
                    if (sType.equals("3")) list.sort(BY_BIRTH);
                    System.out.println("Відсортовано.");
                    break;
                case "4":
                    System.out.print("Введіть частину прізвища: ");
                    String query = scanner.nextLine().toLowerCase();
                    System.out.println("Результати:");
                    boolean found = false;
                    for (AddressEntry e : list) {
                        if (e.getFullName().toLowerCase().contains(query)) {
                            System.out.println(e);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Нічого не знайдено.");
                    break;
                case "5":
                    System.out.print("Введіть номер (1-" + list.size() + "): ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine()) - 1;
                        list.remove(idx); // Стандартний метод remove
                        System.out.println("Видалено.");
                    } catch (Exception e) {
                        System.out.println("Помилка індексу.");
                    }
                    break;
                case "6":
                    System.out.println("1-Save, 2-Load");
                    if (scanner.nextLine().equals("1")) saveToXML(list, "data15.xml");
                    else list = loadFromXML("data15.xml");
                    break;
                case "7":
                    running = false;
                    break;
            }
        }
    }

    // --- Методи збереження (аналогічно Лаб 9, але для List) ---

    private static void saveToXML(List<AddressEntry> list, String filename) {
        try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))) {
            e.writeObject(list);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<AddressEntry> loadFromXML(String filename) {
        try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))) {
            return (List<AddressEntry>) d.readObject();
        } catch (Exception ex) {
            System.out.println("Помилка завантаження: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
}
