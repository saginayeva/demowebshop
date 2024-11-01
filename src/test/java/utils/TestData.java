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
        return faker.internet().password(5, 8, true, false, true);
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

    public Date getDateOfBirth() {
        return faker.date().birthday();
    }

    public String getPicture() {
        return "1000.jpg";
    }

    public String getSubjects() {
        return faker.options().option("Maths", "Physics", "Computer Science", "Economics", "Arts", "History");
    }

    public String getState() {
        return faker.options().option("NCR", "Uttar Pradesh", "Haryana");
    }

    public String getCityForState(String state) {
        switch (state) {
            case "NCR":
                return faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh":
                return faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana":
                return faker.options().option("Karnal", "Panipat");
            default:
                return state;
        }
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