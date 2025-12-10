package ua.khpi.oop.vetrichenko03;

/**
 * Головний клас для Лабораторної роботи №3.
 * Тема: Утилітарні класи. Обробка масивів і рядків.
 * <p>
 * Варіант 3: Розбити рядки на дві групи відносно середньої довжини.
 * </p>
 *
 * @author Ветріченко Дарина
 * @group КН-924з
 * @version 1.0
 */
public class Main {

    /**
     * Точка входу в програму.
     * Ініціалізує тестові дані та демонструє роботу методів класу {@link StringHelper}.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("Start Lab 3...");

        // 1. Вхідні дані (масив рядків)
        // Використовуємо латиницю згідно з вимогами
        String[] textLines = {
                "Java",                 // 4
                "Object Oriented Programming", // 27
                "Hello World",          // 11
                "Code",                 // 4
                "University",           // 10
                "A",                    // 1
                "StringBuilder usage"   // 19
        };

        System.out.println("\nВхідні рядки:");
        for (String s : textLines) {
            System.out.println("- " + s + " (" + s.length() + ")");
        }

        // 2. Обчислення середньої довжини через утилітарний клас
        double average = StringHelper.calculateAverageLength(textLines);
        System.out.printf("\nСередня довжина рядка: %.2f%n\n", average);

        // 3. Формування та виведення звіту для першої групи (Менше середнього)
        String reportLess = StringHelper.getGroupReport(textLines, average, true);
        System.out.println(reportLess);

        // 4. Формування та виведення звіту для другої групи (Більше або рівне середньому)
        String reportMore = StringHelper.getGroupReport(textLines, average, false);
        System.out.println(reportMore);

        System.out.println("End of Lab 3.");
    }
}
