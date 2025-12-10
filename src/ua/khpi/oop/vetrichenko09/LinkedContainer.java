package ua.khpi.oop.vetrichenko09;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Власний параметризований контейнер на основі зв'язного списку.
 * Аналог java.util.LinkedList.
 *
 * @param <T> тип даних, що зберігаються в контейнері
 * @author Ветріченко Дарина
 * @version 1.0
 */
public class LinkedContainer<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Внутрішній клас "Вузол".
     * Зберігає дані та посилання на наступний елемент.
     */
    private static class Node<E> implements Serializable {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> head; // Початок списку
    private Node<T> tail; // Кінець списку (для швидкого додавання)
    private int size;     // Кількість елементів

    public LinkedContainer() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Додає елемент у кінець списку.
     */
    public void add(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode; // Старий хвіст посилається на новий
            tail = newNode;      // Новий вузол стає хвостом
        }
        size++;
    }

    /**
     * Видаляє перше входження елемента.
     */
    public boolean remove(T element) {
        if (head == null) return false;

        // Якщо видаляємо голову (перший елемент)
        if (head.element.equals(element)) {
            head = head.next;
            size--;
            if (size == 0) tail = null;
            return true;
        }

        // Шукаємо в решті списку
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.element.equals(element)) {
                // Знайшли! Перекидаємо посилання через один елемент
                current.next = current.next.next;
                // Якщо видалили останній, оновлюємо tail
                if (current.next == null) {
                    tail = current;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Очищує список.
     */
    public void clear() {
        // Допомагаємо GC (Garbage Collector)
        Node<T> x = head;
        while (x != null) {
            Node<T> next = x.next;
            x.element = null;
            x.next = null;
            x = next;
        }
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(T element) {
        for (T t : this) {
            if (t.equals(element)) return true;
        }
        return false;
    }

    /**
     * Перетворює список у масив об'єктів.
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<T> x = head; x != null; x = x.next)
            result[i++] = x.element;
        return result;
    }

    @Override
    public String toString() {
        if (head == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> x = head; x != null; x = x.next) {
            sb.append(x.element);
            if (x.next != null) sb.append(",\n ");
        }
        sb.append("]");
        return sb.toString();
    }

    // --- Реалізація Ітератора ---

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T data = current.element;
                current = current.next;
                return data;
            }
        };
    }
}