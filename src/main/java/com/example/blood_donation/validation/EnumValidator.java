package com.example.blood_donation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Stream;


@Slf4j
public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(EnumValue enumValue) {

        acceptedValues = Stream.of(enumValue.enumClass().getEnumConstants())
                .map(Enum::toString).toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return acceptedValues.contains(value);
    }
}
