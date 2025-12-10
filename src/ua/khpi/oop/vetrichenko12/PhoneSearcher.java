package ua.khpi.oop.vetrichenko12;

import ua.khpi.oop.vetrichenko07.AddressEntry;
import ua.khpi.oop.vetrichenko10.LinkedContainer;

import java.util.regex.Pattern;

/**
 * Утилітарний клас для пошуку контактів за допомогою регулярних виразів.
 */
public class PhoneSearcher {

    // Регулярні вирази для кодів операторів
    // Формат: (початок рядка, опціонально +38), код, 7 цифр
    private static final String REGEX_KHARKIV = "^(\\+38)?057\\d{7}$";
    private static final String REGEX_LIFE = "^(\\+38)?0(63|93|73)\\d{7}$";
    private static final String REGEX_KYIVSTAR = "^(\\+38)?0(67|68|96|97|98)\\d{7}$";

    private static final Pattern PATTERN_KHARKIV = Pattern.compile(REGEX_KHARKIV);
    private static final Pattern PATTERN_LIFE = Pattern.compile(REGEX_LIFE);
    private static final Pattern PATTERN_KYIVSTAR = Pattern.compile(REGEX_KYIVSTAR);

    /**
     * Знаходить та виводить всі контакти, які відповідають умові:
     * Мають Харківський номер AND Lifecell AND Kyivstar.
     */
    public static void printMatchingContacts(LinkedContainer<AddressEntry> container) {
        System.out.println("\n--- Результати пошуку (Kharkiv + Life + KS) ---");
        boolean foundAny = false;

        for (AddressEntry entry : container) {
            if (matchesCriteria(entry)) {
                System.out.println("ЗНАЙДЕНО: " + entry.getFullName());
                System.out.println("Телефони: " + entry.getPhones());
                System.out.println("-------------------------");
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("Записів не знайдено.");
        }
    }

    /**
     * Перевіряє один запис на відповідність умовам.
     */
    private static boolean matchesCriteria(AddressEntry entry) {
        boolean hasKharkiv = false;
        boolean hasLife = false;
        boolean hasKyivstar = false;

        // Перевіряємо кожен телефон у списку контактів
        for (String phone : entry.getPhones()) {
            // Видаляємо пробіли та дужки, якщо вони є (для чистоти експерименту)
            String cleanPhone = phone.replaceAll("[\\s\\-\\(\\)]", "");

            if (PATTERN_KHARKIV.matcher(cleanPhone).matches()) {
                hasKharkiv = true;
            } else if (PATTERN_LIFE.matcher(cleanPhone).matches()) {
                hasLife = true;
            } else if (PATTERN_KYIVSTAR.matcher(cleanPhone).matches()) {
                hasKyivstar = true;
            }
        }

        // Повертаємо true тільки якщо є ВСІ три типи
        return hasKharkiv && hasLife && hasKyivstar;
    }
}