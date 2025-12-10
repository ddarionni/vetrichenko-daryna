package ua.khpi.oop.vetrichenko03;

/**
 * Утилітарний клас для обробки масивів рядків.
 * Містить статичні методи для розрахунків середньої довжини та фільтрації рядків.
 * <p>
 * Цей клас не може бути інстанційований.
 * </p>
 *
 * @author Ветріченко Дарина
 *
 */
public class StringHelper {

    /**
     * Приватний конструктор, щоб заборонити створення екземплярів цього класу.
     * Це стандартна практика для утилітарних класів (Utility classes).
     */
    private StringHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Обчислює середню довжину рядків у заданому масиві.
     * Ігнорує null-елементи.
     *
     * @param strings масив рядків для аналізу
     * @return середня довжина рядка (double). Повертає 0, якщо масив порожній або null.
     */
    public static double calculateAverageLength(String[] strings) {
        if (strings == null || strings.length == 0) {
            return 0;
        }

        int totalLength = 0;
        for (String s : strings) {
            if (s != null) {
                totalLength += s.length();
            }
        }
        return (double) totalLength / strings.length;
    }

    /**
     * Формує текстовий звіт про рядки, які відповідають умові фільтрації.
     * Використовує {@link StringBuilder} для ефективного з'єднання рядків.
     *
     * @param strings         масив вхідних рядків
     * @param average         значення середньої довжини для порівняння
     * @param lessThanAverage прапор умови:
     * true - шукати рядки коротші за середнє;
     * false - шукати рядки довші або рівні середньому.
     * @return відформатований рядок з переліком знайдених елементів
     */
    public static String getGroupReport(String[] strings, double average, boolean lessThanAverage) {
        StringBuilder sb = new StringBuilder();

        // Формуємо заголовок групи
        if (lessThanAverage) {
            sb.append("--- Група: Рядки коротші за середнє (< ")
                    .append(String.format("%.2f", average))
                    .append(") ---\n");
        } else {
            sb.append("--- Група: Рядки довші або рівні середньому (>= ")
                    .append(String.format("%.2f", average))
                    .append(") ---\n");
        }

        // Проходимо по масиву і вибираємо потрібні
        boolean found = false;
        for (String s : strings) {
            if (s == null) continue;

            boolean condition;
            if (lessThanAverage) {
                condition = s.length() < average;
            } else {
                condition = s.length() >= average;
            }

            if (condition) {
                sb.append(" [Len: ").append(s.length()).append("] ").append(s).append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append(" (Рядків не знайдено)\n");
        }

        return sb.toString();
    }
}