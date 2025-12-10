package ua.khpi.oop.vetrichenko11;

import java.util.regex.Pattern;

/**
 * Утилітарний клас для перевірки даних за допомогою регулярних виразів.
 */
public class RegexValidator {

    // Шаблон для ПІБ: Починається з великої, мінімум два слова через пробіл
    // Підтримує латиницю, кирилицю, апостроф
    private static final String NAME_PATTERN =
            "^[A-ZА-ЯІЇЄ][a-zа-яіїє']+(\\s[A-ZА-ЯІЇЄ][a-zа-яіїє']+)+$";

    // Шаблон для дати: DD.MM.YYYY
    private static final String DATE_PATTERN =
            "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$";

    // Шаблон для телефону: +380XXXXXXXXX або 0XXXXXXXXX
    private static final String PHONE_PATTERN =
            "^(\\+38)?0\\d{9}$";

    // Шаблон для адреси: Не порожній рядок, мінімум 3 символи
    private static final String ADDRESS_PATTERN =
            "^.{3,}$";

    // --- Методи перевірки ---

    public static boolean isValidName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }

    public static boolean isValidDate(String date) {
        return Pattern.matches(DATE_PATTERN, date);
    }

    public static boolean isValidPhone(String phone) {
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    public static boolean isValidAddress(String address) {
        if (address == null) return false;
        return Pattern.matches(ADDRESS_PATTERN, address.trim());
    }
}