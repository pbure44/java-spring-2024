package com.example.demo.validation;

import com.example.demo.properties.ReferenceDataProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    @Value("${reference-data.categories:}#{T(java.util.Collections).emptyList()}")
    private List<String> categories;

//    @Override
//    public void initialize(ValidCategory constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categories.contains(value);
    }


}
