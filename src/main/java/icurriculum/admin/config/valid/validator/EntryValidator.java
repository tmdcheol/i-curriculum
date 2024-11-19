package icurriculum.admin.config.valid.validator;

import icurriculum.admin.config.valid.annotation.ValidEntry;
import icurriculum.admin.web.form.detail.Entry;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EntryValidator implements ConstraintValidator<ValidEntry, Entry> {

    @Override
    public boolean isValid(Entry entry, ConstraintValidatorContext context) {
        return entry.getKey() != null && !entry.getKey().isBlank() && !entry.getKey().isEmpty() &&
                entry.getValue() != null && !entry.getValue().isBlank() && !entry.getValue()
                .isEmpty();
    }
}

