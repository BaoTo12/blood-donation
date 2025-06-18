package com.example.blood_donation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;


public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(EnumValue enumValue) {

        acceptedValues = Stream.of(enumValue.enumClass().getEnumConstants())
                .map(Enum::name).toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return acceptedValues.contains(value);
    }
}
