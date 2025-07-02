package com.example.blood_donation.validation;

import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DataValidRangeValidator implements ConstraintValidator<DataValidRange, Object> {
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(DataValidRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.startFieldName();
        this.endFieldName = constraintAnnotation.endFieldName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Field startField = object.getClass().getDeclaredField(this.startFieldName);
            Field endField = object.getClass().getDeclaredField(this.endFieldName);

            startField.setAccessible(true);
            endField.setAccessible(true);

            LocalDate startDate = (LocalDate) startField.get(object);
            LocalDate endDate = (LocalDate) endField.get(object);

            // If either date is null, let other validators handle it
            // This follows the principle of single responsibility
            if (startDate == null || endDate == null) {
                return true;
            }

            return !startDate.isAfter(endDate);
        } catch (NoSuchFieldException e) {
            throw new AppException(ErrorCode.FIELD_NOT_EXIST, startFieldName + " or " + endFieldName + "do not exist");
        } catch (IllegalAccessException e) {
            // TODO: TEMPORARY
            throw new RuntimeException(e);
        }


    }


}
