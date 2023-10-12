package com.shoggoth.hibernateapp.servise.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationUtils {
    private static Validator validator;
    public static final String WRONG_FIRST_NAME_FORMAT = "Wrong firstname format";
    public static final String WRONG_LAST_NAME_FORMAT = "Wrong lastname format";
    public static final String WRONG_SKILL_NAME_FORMAT = "Wrong skill name format";
    public static final String WRONG_SPECIALTY_NAME_FORMAT = "Wrong specialty name format";

    public static Validator getValidator() {
        if (validator == null) {
            try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
                validator = validatorFactory.getValidator();
            }
        }
        return validator;
    }
}
