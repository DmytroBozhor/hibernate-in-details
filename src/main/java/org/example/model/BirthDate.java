package org.example.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record BirthDate(LocalDate birthDate) {

    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

}
