package ua.khpi.oop.vetrichenko16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для LinkedContainer (Лаб 9).
 */
class ContainerTest {

    private LinkedContainer<String> container;

    @BeforeEach
    void setUp() {
        // Перед кожним тестом створюємо новий чистий контейнер
        container = new LinkedContainer<>();
    }

    @Test
    void testAddAndSize() {
        assertEquals(0, container.size());
        container.add("One");
        container.add("Two");
        assertEquals(2, container.size());
    }

    @Test
    void testGet() {
        container.add("First");
        container.add("Second");
        assertEquals("First", container.get(0));
        assertEquals("Second", container.get(1));
    }

    @Test
    void testGetOutOfBounds() {
        container.add("A");
        // Перевіряємо, що викидається помилка при зверненні до неіснуючого індексу
        assertThrows(IndexOutOfBoundsException.class, () -> container.get(5));
    }

    @Test
    void testRemove() {
        container.add("A");
        container.add("B");
        container.add("C");

        boolean removed = container.remove("B");
        assertTrue(removed);
        assertEquals(2, container.size());
        assertEquals("C", container.get(1)); // C зсунулося на місце B

        boolean notRemoved = container.remove("Z");
        assertFalse(notRemoved);
    }

    @Test
    void testClear() {
        container.add("Data");
        container.clear();
        assertEquals(0, container.size());
    }

    @Test
    void testIterator() {
        container.add("1");
        container.add("2");

        Iterator<String> it = container.iterator();
        assertTrue(it.hasNext());
        assertEquals("1", it.next());
        assertTrue(it.hasNext());
        assertEquals("2", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testToString() {
        container.add("Hello");
        String str = container.toString();
        assertTrue(str.contains("Hello"));
    }
}
