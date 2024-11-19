package icurriculum.admin.config.valid.validator;

import icurriculum.admin.config.valid.annotation.ValidCategory;
import icurriculum.domain.take.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank() || value.isEmpty()) {
            return false;
        }

        return Category.isValid(value);
    }
}
