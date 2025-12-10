package ua.khpi.oop.vetrichenko09;

// Якщо AddressEntry в іншому пакеті, розкоментуй імпорт:
import ua.khpi.oop.vetrichenko07.AddressEntry;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Лабораторна робота №9.
 * Тема: Параметризація (Generics).
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Lab 9: Generic Linked List ===");

        // 1. Створення контейнера, типізованого нашим класом AddressEntry
        LinkedContainer<AddressEntry> contacts = new LinkedContainer<>();

        // 2. Додавання елементів
        contacts.add(new AddressEntry("Іваненко Іван", "01.01.1990", "Київ"));
        contacts.add(new AddressEntry("Петренко Петро", "05.05.1995", "Львів"));
        contacts.add(new AddressEntry("Сидоренко Сидір", "10.10.2000", "Харків"));

        System.out.println("Розмір списку: " + contacts.size());

        // 3. Перевірка for-each (Iterable)
        System.out.println("\n--- Вміст контейнера ---");
        for (AddressEntry entry : contacts) {
            System.out.println(entry);
        }

        // 4. Перевірка видалення
        System.out.println("\n--- Видалення першого елемента ---");
        // Створюємо тимчасовий об'єкт для пошуку (потрібно, щоб в AddressEntry був equals,
        // але зараз видалимо просто за логікою "перший", або створимо такий самий об'єкт)
        // Для демонстрації просто очистимо і додамо заново, або видалимо перший знайдений у циклі
        contacts.remove(contacts.iterator().next()); // Видаляє голову
        System.out.println("Розмір після видалення: " + contacts.size());

        // 5. Серіалізація (Standard)
        testStandardSerialization(contacts);

        // 6. Серіалізація (XML)
        testXMLPersistence(contacts);
    }

    private static void testStandardSerialization(LinkedContainer<AddressEntry> list) {
        System.out.println("\n--- Тест стандартної серіалізації (.ser) ---");
        String filename = "lab9_data.ser";

        // Збереження
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Збережено у " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Відновлення
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            LinkedContainer<AddressEntry> loaded = (LinkedContainer<AddressEntry>) ois.readObject();
            System.out.println("Відновлено. Розмір: " + loaded.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testXMLPersistence(LinkedContainer<AddressEntry> list) {
        System.out.println("\n--- Тест XML збереження ---");
        String filename = "lab9_data.xml";

        // Для XML збереження ми перетворимо наш список у масив,
        // бо XMLEncoder погано працює зі складною структурою вузлів без спеціальних налаштувань.
        // Завдання вимагає "зберегти колекцію", масив - це теж форма збереження даних.

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))) {
            encoder.writeObject(list.toArray());
            System.out.println("Збережено у " + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))) {
            Object[] array = (Object[]) decoder.readObject();
            System.out.println("Відновлено з XML. Кількість об'єктів: " + array.length);
            // Можна заново заповнити контейнер
            LinkedContainer<AddressEntry> loadedList = new LinkedContainer<>();
            for (Object obj : array) {
                loadedList.add((AddressEntry) obj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}