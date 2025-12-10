package ua.khpi.oop.vetrichenko12;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedContainer<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private static class Node<E> implements Serializable {
        E element;
        Node<E> next;
        Node(E element, Node<E> next) { this.element = element; this.next = next; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void add(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (head == null) { head = newNode; tail = newNode; }
        else { tail.next = newNode; tail = newNode; }
        size++;
    }

    public int size() { return size; }

    public void clear() { head = tail = null; size = 0; }

    // --- Методи для доступу за індексом (потрібні для сортування) ---

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.element;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        current.element = element;
    }

    // --- Стандартні методи ---

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<T> x = head; x != null; x = x.next) result[i++] = x.element;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            @Override
            public boolean hasNext() { return current != null; }
            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T data = current.element;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T item : this) sb.append(item).append("\n");
        return sb.toString();
    }
}