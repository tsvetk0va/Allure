package ru.netology.data;


import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int plusdays) {
        LocalDate todayPlus = LocalDate.now();
        LocalDate date = todayPlus.plusDays(plusdays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String deliveryDate = date.format(formatter);
        return deliveryDate;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateByCard(String locale) {
            return new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale));
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;
    }
}

