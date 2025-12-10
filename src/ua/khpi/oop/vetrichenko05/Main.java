package ua.khpi.oop.vetrichenko05;

import java.util.Iterator;

/**
 * Лабораторна робота №5.
 * Тема: Власні контейнери та ітератори.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Lab 5 Start ===");

        // 1. Створення контейнера
        StringContainer container = new StringContainer();

        // 2. Додавання елементів (add)
        container.add("Apple");
        container.add("Banana");
        container.add("Cherry");
        container.add("Date");
        container.add("Elderberry");

        System.out.println("Початковий контейнер: " + container.toString());
        System.out.println("Розмір: " + container.size());

        // 3. Перевірка contains
        System.out.println("\nМістить 'Banana'? " + container.contains("Banana"));
        System.out.println("Містить 'Pizza'? " + container.contains("Pizza"));

        // 4. Демонстрація remove(String)
        System.out.println("\nВидаляємо 'Cherry'...");
        container.remove("Cherry");
        System.out.println("Після видалення: " + container);

        // 5. Демонстрація Iterator (цикл for-each)
        System.out.println("\n--- Ітерація через for-each ---");
        for (String s : container) {
            System.out.println("Element: " + s);
        }

        // 6. Демонстрація Iterator (цикл while + remove)
        System.out.println("\n--- Ітерація через while та видалення слів на 'A' ---");
        Iterator<String> it = container.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.startsWith("A")) {
                System.out.println("Видаляємо ітератором: " + s);
                it.remove();
            }
        }
        System.out.println("Контейнер після ітератора: " + container);

        // 7. toArray
        System.out.println("\n--- Перетворення в масив (toArray) ---");
        Object[] arr = container.toArray();
        for (Object o : arr) {
            System.out.print(o + " ");
        }
        System.out.println();

        // 8. Clear
        System.out.println("\nОчищення контейнера...");
        container.clear();
        System.out.println("Розмір після clear: " + container.size());
        System.out.println("Вміст: " + container);

        System.out.println("=== Lab 5 End ===");
    }
}