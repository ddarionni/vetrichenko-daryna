package ua.khpi.oop.vetrichenko05;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Власний контейнер для зберігання рядків.
 * Реалізує динамічний масив та інтерфейс Iterable.
 *
 * @author Ветріченко Дарина
 * @version 1.0
 */
public class StringContainer implements Iterable<String> {

    /** Внутрішній масив для зберігання даних. */
    private String[] data;

    /** Поточна кількість елементів у контейнері. */
    private int size;

    /** Початкова ємність масиву за замовчуванням. */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Конструктор за замовчуванням.
     */
    public StringContainer() {
        this.data = new String[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Конструктор з масиву (для зручності).
     * @param initialData початковий масив
     */
    public StringContainer(String[] initialData) {
        this.data = new String[initialData.length + DEFAULT_CAPACITY];
        this.size = 0;
        for (String s : initialData) {
            add(s);
        }
    }

    /**
     * Додає елемент до кінця контейнера.
     * Якщо місця немає, масив збільшується.
     * @param string рядок для додавання
     */
    public void add(String string) {
        if (size == data.length) {
            resize();
        }
        data[size++] = string;
    }

    /**
     * Повертає елемент за індексом.
     */
    public String get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }

    /**
     * Повертає кількість елементів.
     */
    public int size() {
        return size;
    }

    /**
     * Очищує контейнер.
     */
    public void clear() {
        // Зануляємо посилання для збирача сміття (GC)
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Видаляє перше входження елемента.
     * @param string елемент, який треба видалити
     * @return true, якщо елемент знайдено і видалено
     */
    public boolean remove(String string) {
        if (string == null) return false;

        for (int i = 0; i < size; i++) {
            if (string.equals(data[i])) {
                removeAtIndex(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Допоміжний метод видалення за індексом (зі зсувом масиву).
     */
    private void removeAtIndex(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // Зсуваємо всі елементи вліво на 1 позицію
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null; // Видаляємо останній дубль (для GC)
    }

    /**
     * Перевіряє наявність елемента.
     */
    public boolean contains(String string) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Перевіряє, чи містяться всі елементи з іншого контейнера.
     */
    public boolean containsAll(StringContainer other) {
        for (String item : other) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Повертає копію даних у вигляді масиву.
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Збільшує розмір внутрішнього масиву в 1.5 рази.
     */
    private void resize() {
        int newCapacity = (data.length * 3) / 2 + 1;
        String[] newData = new String[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    // --- Реалізація Ітератора ---

    @Override
    public Iterator<String> iterator() {
        return new ContainerIterator();
    }

    /**
     * Внутрішній клас ітератора.
     * Має доступ до приватних полів зовнішнього класу.
     */
    private class ContainerIterator implements Iterator<String> {
        /** Індекс наступного елемента для повернення. */
        private int cursor;

        /** Індекс останнього повернутого елемента (-1 якщо нічого не повертали). */
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public String next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            String current = data[cursor];
            lastRet = cursor;
            cursor++;
            return current;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            // Викликаємо метод зовнішнього класу
            StringContainer.this.removeAtIndex(lastRet);

            // Оскільки ми видалили елемент і зсунули масив, курсор треба повернути назад
            cursor = lastRet;
            lastRet = -1; // Скидаємо, щоб не можна було викликати remove двічі поспіль
        }
    }
}