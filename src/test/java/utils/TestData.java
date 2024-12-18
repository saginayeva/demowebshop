package utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestData {
    private final Faker faker = new Faker(Locale.ENGLISH);

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getUserEmail() {
        return faker.internet().emailAddress();
    }

    public String getPassword() {
        return faker.internet().password(6, 9, true, true, true);
    }

    public String getPhoneNumber() {
        return faker.number().digits(7);
    }

    public String getStreetAddress() {
        return faker.address().streetAddress();
    }

    public String getGender() {
        return faker.options().option("Male", "Female");
    }

    public String getDay(Date date) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        return dayFormat.format(date);
    }

    public String getMonth(Date date) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        return monthFormat.format(date);
    }

    public String getYear(Date date) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return yearFormat.format(date);
    }
}
