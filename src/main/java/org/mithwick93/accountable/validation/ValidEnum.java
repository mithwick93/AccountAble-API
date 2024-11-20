package org.mithwick93.accountable.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Documented
@Constraint(validatedBy = ValidEnum.EnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    String message() default "must be any of enum {enumClass}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    class EnumValidator implements ConstraintValidator<ValidEnum, String> {

        private Set<String> validValues;

        @Override
        public void initialize(ValidEnum annotation) {
            validValues = Arrays.stream(annotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toSet());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            return validValues.contains(value);
        }

    }

}
