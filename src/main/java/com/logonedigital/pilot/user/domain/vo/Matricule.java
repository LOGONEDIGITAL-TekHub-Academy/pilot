package com.logonedigital.pilot.user.domain.vo;
import com.logonedigital.pilot.shared.domain.RegexPatterns;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public record Matricule(@NonNull String value) {
    public Matricule {
        if (!value.matches(RegexPatterns.MATRICULE)) {
            throw new IllegalArgumentException(
                    "Matricule must follow XXXTKDMDDYY format (ex: 181TKDM2925)"
            );
        }
    }

    public int getYear() {
        return Integer.parseInt(value.substring(4));
    }

    public static Matricule generate() {
        // Generate random 3-digit number (100-999)
        int randomNum = ThreadLocalRandom.current().nextInt(100, 1000);

        // Get current date components
        LocalDate currentDate = LocalDate.now();
        int dayOfMonth = currentDate.getDayOfMonth();
        int yearLastTwoDigits = currentDate.getYear() % 100;

        // Format the matricule
        String matriculeValue = String.format("%dTKDM%02d%02d",
                randomNum, dayOfMonth, yearLastTwoDigits);

        return new Matricule("STD-" + currentDate.getYear()); // Note: You might want to adjust this
    }
}