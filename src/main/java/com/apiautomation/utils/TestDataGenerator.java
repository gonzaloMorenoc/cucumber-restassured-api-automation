package com.apiautomation.utils;

import com.apiautomation.models.User;
import java.util.Random;

public class TestDataGenerator {
    
    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"John", "Jane", "Mike", "Sarah", "David", "Emma", "Robert", "Lisa"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
    private static final String[] GENDERS = {"male", "female"};
    private static final String[] STATUSES = {"active", "inactive"};

    public static User generateRandomUser() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String name = firstName + " " + lastName;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + 
                      random.nextInt(1000) + "@testmail.com";
        String gender = GENDERS[random.nextInt(GENDERS.length)];
        String status = STATUSES[random.nextInt(STATUSES.length)];
        
        return new User(name, email, gender, status);
    }

    public static User generateUserWithName(String name) {
        User user = generateRandomUser();
        user.setName(name);
        return user;
    }

    public static User generateUserWithEmail(String email) {
        User user = generateRandomUser();
        user.setEmail(email);
        return user;
    }

    public static User generateUserWithGender(String gender) {
        User user = generateRandomUser();
        user.setGender(gender);
        return user;
    }

    public static User generateUserWithStatus(String status) {
        User user = generateRandomUser();
        user.setStatus(status);
        return user;
    }

    public static User generateInvalidUser() {
        return new User("", "", "invalid_gender", "invalid_status");
    }
}