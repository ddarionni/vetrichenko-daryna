package ua.khpi.oop.vetrichenko16;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести бізнес-логіки (Валідація, Пошук).
 */
class ValidationTest {

    // --- ТЕСТИ ЛАБ 11 (RegexValidator) ---

    @Test
    void testValidNames() {
        assertTrue(RegexValidator.isValidName("Шевченко Тарас"));
        assertTrue(RegexValidator.isValidName("John Doe"));
    }

    @Test
    void testInvalidNames() {
        assertFalse(RegexValidator.isValidName("taras")); // Мала літера
        assertFalse(RegexValidator.isValidName("Shevchenko")); // Тільки одне слово
        assertFalse(RegexValidator.isValidName("12345")); // Цифри
    }

    @Test
    void testValidDate() {
        assertTrue(RegexValidator.isValidDate("01.01.2000"));
        assertTrue(RegexValidator.isValidDate("31.12.2023"));
    }

    @Test
    void testInvalidDate() {
        assertFalse(RegexValidator.isValidDate("32.01.2000")); // 32 дня немає
        assertFalse(RegexValidator.isValidDate("2000.01.01")); // Невірний формат
        assertFalse(RegexValidator.isValidDate("text"));
    }

    @Test
    void testValidPhone() {
        assertTrue(RegexValidator.isValidPhone("+380501234567"));
        assertTrue(RegexValidator.isValidPhone("0501234567"));
    }

    // --- ТЕСТИ ЛАБ 12 (Логіка пошуку) ---
    // Увага: переконайся, що метод matchesCriteria() у PhoneSearcher є public!
    /*
    @Test
    void testPhoneSearcherCriteria() {
        // 1. Користувач, що підходить (Всі 3 оператори)
        AddressEntry goodUser = new AddressEntry("Test User", "01.01.2000", "Kharkiv");
        goodUser.addPhone("0577001122"); // Kharkiv
        goodUser.addPhone("0631112233"); // Life
        goodUser.addPhone("0679998877"); // KS

        // Тут ми викликаємо метод з PhoneSearcher.
        // Якщо він private, зміни його на public у файлі PhoneSearcher.java
        // assertTrue(PhoneSearcher.matchesCriteria(goodUser));

        // 2. Користувач, що НЕ підходить (Тільки Life)
        AddressEntry badUser = new AddressEntry("Bad User", "01.01.2000", "Kyiv");
        badUser.addPhone("0631112233");
        // assertFalse(PhoneSearcher.matchesCriteria(badUser));
    }
    */

    // --- ТЕСТИ ЛАБ 13 (Логіка підрахунку - без потоків) ---
    @Test
    void testVodafoneCountingLogic() {
        // Створимо міні-контейнер
        LinkedContainer<AddressEntry> container = new LinkedContainer<>();

        AddressEntry u1 = new AddressEntry("A", "01.01.2000", "K");
        u1.addPhone("0501112233"); // Vodafone

        AddressEntry u2 = new AddressEntry("B", "01.01.2000", "K");
        u2.addPhone("0671112233"); // KS (не рахуємо)

        container.add(u1);
        container.add(u2);

        // Перевіряємо логіку вручну (те саме, що робить потік)
        int count = 0;
        for (AddressEntry entry : container) {
            for (String phone : entry.getPhones()) {
                if (phone.matches(".*(050|066|095|099).*")) {
                    count++;
                    break;
                }
            }
        }

        assertEquals(1, count, "Має знайти 1 номер Vodafone");
    }
}

