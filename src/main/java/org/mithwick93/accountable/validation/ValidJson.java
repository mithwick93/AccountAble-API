package org.mithwick93.accountable.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.mithwick93.accountable.util.JsonUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidJson.JsonValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidJson {

    String message() default "Invalid JSON format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class JsonValidator implements ConstraintValidator<ValidJson, String> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isEmpty()) {
                return true;
            }

            return JsonUtil.validateJson(value);
        }

    }

}
