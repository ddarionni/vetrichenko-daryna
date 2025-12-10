package ua.khpi.oop.vetrichenko01;

/**
 * Лабораторна робота №1.
 * Тема: Структура програми, типи даних, літерали.
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {


    public static void main(String[] args) {
        // 1. Ініціалізація змінних за допомогою літералів


        // 54321 (dec) -> D431 (hex)
        int studentId = 0xD431;

        // Номер телефону: 380962849509
        long phoneNumber = 380962849509L;

        // Останні 2 ненульові цифри телефону: ...9509 -> 5, 9 -> число 59
        // 59 (dec) -> 111011 (bin)
        int lastTwoDigits = 0b111011;

        // Останні 4 ненульові цифри телефону: ...849509 -> 8, 4, 5, 9 -> число 8459
        // 8459 (dec) -> 20413 (oct)
        int lastFourDigits = 020413;

        // Номер у списку групи
        int groupListNumber = 3;

        // 2. Обчислення символу
        // Формула: ((N - 1) % 26) + 1
        // Для N=3: ((2 % 26) + 1) = 3 -> Третя літера алфавіту -> 'C'
        int charIndex = ((groupListNumber - 1) % 26) + 1;
        char myChar = (char) ('A' + charIndex - 1);

        // 3. Обробка даних (підрахунок цифр та бітів)

        // --- Обробка studentId ---
        int idEven = countEvenDigits(studentId);
        int idOdd = countOddDigits(studentId);
        int idOnes = countBinaryOnes(studentId);

        // --- Обробка phoneNumber ---
        int phoneEven = countEvenDigits(phoneNumber);
        int phoneOdd = countOddDigits(phoneNumber);
        int phoneOnes = countBinaryOnes(phoneNumber);

        // --- Обробка lastTwoDigits ---
        int twoDigEven = countEvenDigits(lastTwoDigits);
        int twoDigOdd = countOddDigits(lastTwoDigits);
        int twoDigOnes = countBinaryOnes(lastTwoDigits);

        // --- Обробка lastFourDigits ---
        int fourDigEven = countEvenDigits(lastFourDigits);
        int fourDigOdd = countOddDigits(lastFourDigits);
        int fourDigOnes = countBinaryOnes(lastFourDigits);

        // ТУТ СТАВИМО BREAKPOINT (ЧЕРВОНУ КРАПКУ)
        // Дивимось значення змінних у вкладці Debugger -> Variables
    }


    public static int countEvenDigits(long number) {
        int count = 0;
        long temp = Math.abs(number);
        if (temp == 0) return 1;

        while (temp > 0) {
            long digit = temp % 10;
            if (digit % 2 == 0) {
                count++;
            }
            temp /= 10;
        }
        return count;
    }


    public static int countOddDigits(long number) {
        int count = 0;
        long temp = Math.abs(number);
        if (temp == 0) return 0;

        while (temp > 0) {
            long digit = temp % 10;
            if (digit % 2 != 0) {
                count++;
            }
            temp /= 10;
        }
        return count;
    }


    public static int countBinaryOnes(long number) {
        return Long.bitCount(number);
    }
}
