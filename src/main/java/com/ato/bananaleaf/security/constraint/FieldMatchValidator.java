package com.ato.bananaleaf.security.constraint;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            final Object firstObject = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObject = BeanUtils.getProperty(value, secondFieldName);

            return firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
        } catch (Exception ignore) {}

        return true;
    }
}
