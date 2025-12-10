package ua.khpi.oop.vetrichenko02;

import java.util.Random;

/**
 * Лабораторна робота №2.
 * Тема: Алгоритмічна декомпозиція. Прості алгоритми обробки даних.
 * Варіант 3: Знайти найбільшу цифру в десятковому запису числа.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {


    public static void main(String[] args) {
        // Заголовок таблиці
        System.out.println("-----------------------------------");
        System.out.printf("| %-15s | %-13s |%n", "Число (Input)", "Макс. цифра");
        System.out.println("-----------------------------------");

        Random random = new Random();

        // Цикл на 10 ітерацій, як вимагає завдання
        for (int i = 0; i < 10; i++) {
            // Генеруємо випадкове ціле число
            int randomNumber = random.nextInt();

            // Викликаємо метод для пошуку цифри
            int maxDigit = findMaxDigit(randomNumber);

            // Виводимо рядок таблиці
            System.out.printf("| %-15d | %-13d |%n", randomNumber, maxDigit);
        }

        System.out.println("-----------------------------------");
    }

    /**
     * Знаходить найбільшу цифру в цілому числі, використовуючи
     * тільки арифметичні операції (без перетворення в String).
     *
     * @param number вхідне число
     * @return найбільша цифра (0-9)
     */
    public static int findMaxDigit(int number) {
        // Працюємо з модулем числа, щоб ігнорувати знак мінус
        int temp = Math.abs(number);

        // Якщо число 0, то найбільша цифра 0
        if (temp == 0) {
            return 0;
        }

        int max = 0;

        // Поки в числі є цифри
        while (temp > 0) {
            // Отримуємо останню цифру (залишок від ділення на 10)
            int digit = temp % 10;

            // Порівнюємо з поточною максимальною
            if (digit > max) {
                max = digit;
            }


            if (max == 9) {
                break;
            }


            temp /= 10;
        }

        return max;
    }
}