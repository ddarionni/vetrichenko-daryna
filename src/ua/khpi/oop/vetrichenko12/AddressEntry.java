package ua.khpi.oop.vetrichenko12;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Доменний клас "Запис в адресній книзі".
 * Відповідає варіанту №3.
 * Реалізує принципи JavaBeans.
 *
 * @author Ветріченко Дарина
 * @version 1.0
 */
public class AddressEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    // Поля сутності (private для інкапсуляції)
    private String fullName;
    private String birthDate;
    private List<String> phones;
    private String address;
    private LocalDateTime lastEditTime;

    /**
     * Конструктор без параметрів (вимога JavaBeans).
     */
    public AddressEntry() {
        this.phones = new ArrayList<>();
        updateTimestamp();
    }

    /**
     * Конструктор з параметрами.
     */
    public AddressEntry(String fullName, String birthDate, String address) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.phones = new ArrayList<>();
        updateTimestamp();
    }

    // --- Геттери та Сеттери ---

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        updateTimestamp(); // Оновлюємо час редагування
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        updateTimestamp();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        updateTimestamp();
    }

    public List<String> getPhones() {
        return phones;
    }

    /**
     * Додати номер телефону.
     */
    public void addPhone(String phone) {
        this.phones.add(phone);
        updateTimestamp();
    }

    public LocalDateTime getLastEditTime() {
        return lastEditTime;
    }

    /**
     * Приватний метод для оновлення часу редагування.
     */
    private void updateTimestamp() {
        this.lastEditTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------\n");
        sb.append("ПІБ: ").append(fullName).append("\n");
        sb.append("Дата народження: ").append(birthDate).append("\n");
        sb.append("Адреса: ").append(address).append("\n");
        sb.append("Телефони: ").append(phones).append("\n");
        sb.append("Останнє редагування: ").append(lastEditTime.format(formatter)).append("\n");
        sb.append("--------------------------------------------------");
        return sb.toString();
    }
}