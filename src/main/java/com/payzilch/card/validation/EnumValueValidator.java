package com.payzilch.card.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {
    private Class<? extends Enum> enumType;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumType = constraintAnnotation.enumType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Set<String> validValues = getEnumNames();
        return validValues.contains(value.toUpperCase()) || setViolation(context);
    }

    private Set<String> getEnumNames() {
        return Arrays.stream(enumType.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    private boolean setViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("Allowed values: [%s]",
                                                                   StringUtils.join(getEnumNames(), ", ")))
                .addConstraintViolation();
        return false;
    }

}
