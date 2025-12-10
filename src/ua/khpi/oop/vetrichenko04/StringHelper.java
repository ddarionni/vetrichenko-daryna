package ua.khpi.oop.vetrichenko04;

/**
 * Утилітарний клас для обробки масивів рядків.
 * Копія з Лабораторної роботи №3.
 *
 * @author Ветріченко Дарина
 * @version 1.0
 */
public class StringHelper {

    private StringHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Обчислює середню довжину рядків у заданому масиві.
     */
    public static double calculateAverageLength(String[] strings) {
        if (strings == null || strings.length == 0) {
            return 0;
        }
        int totalLength = 0;
        int count = 0;
        for (String s : strings) {
            if (s != null) {
                totalLength += s.length();
                count++;
            }
        }
        return count == 0 ? 0 : (double) totalLength / count;
    }

    /**
     * Формує текстовий звіт про рядки.
     */
    public static String getGroupReport(String[] strings, double average, boolean lessThanAverage) {
        StringBuilder sb = new StringBuilder();
        if (lessThanAverage) {
            sb.append("--- Група: Рядки коротші за середнє (< ").append(String.format("%.2f", average)).append(") ---\n");
        } else {
            sb.append("--- Група: Рядки довші або рівні середньому (>= ").append(String.format("%.2f", average)).append(") ---\n");
        }

        boolean found = false;
        for (String s : strings) {
            if (s == null) continue;
            boolean condition = lessThanAverage ? (s.length() < average) : (s.length() >= average);

            if (condition) {
                sb.append(" [Len: ").append(s.length()).append("] ").append(s).append("\n");
                found = true;
            }
        }
        if (!found) sb.append(" (Рядків не знайдено)\n");

        return sb.toString();
    }
}