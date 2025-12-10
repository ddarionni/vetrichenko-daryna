package ua.khpi.oop.vetrichenko10;

import java.util.Comparator;

/**
 * Утилітарний клас з параметризованими методами.
 * Містить алгоритми обробки контейнера.
 */
public class ContainerTools {

    /**
     * Параметризований метод сортування (Generic Method).
     * Використовує алгоритм Bubble Sort.
     *
     * @param container  контейнер для сортування
     * @param comparator логіка порівняння двох елементів
     * @param <T>        тип елементів
     */
    public static <T> void sort(LinkedContainer<T> container, Comparator<T> comparator) {
        int n = container.size();
        if (n < 2) return;

        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                T o1 = container.get(j);
                T o2 = container.get(j + 1);

                // Якщо o1 "більше" за o2 згідно компаратора
                if (comparator.compare(o1, o2) > 0) {
                    // Міняємо місцями
                    container.set(j, o2);
                    container.set(j + 1, o1);
                    swapped = true;
                }
            }
            // Оптимізація: якщо обмінів не було, масив вже відсортований
            if (!swapped) break;
        }
    }
}