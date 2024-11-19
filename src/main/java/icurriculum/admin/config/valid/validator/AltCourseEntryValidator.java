package icurriculum.admin.config.valid.validator;

import icurriculum.admin.config.valid.annotation.ValidAltCourseEntry;
import icurriculum.admin.web.form.detail.AltCourseEntry;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.regex.Pattern;

public class AltCourseEntryValidator implements
        ConstraintValidator<ValidAltCourseEntry, AltCourseEntry> {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}\\d{4}$");

    @Override
    public boolean isValid(AltCourseEntry altCourseEntry, ConstraintValidatorContext context) {
        if (notValidKey(altCourseEntry.getKey())) {
            return false;
        }

        if (notValidValues(altCourseEntry.getValues())) {
            return false;
        }

        return true;
    }

    private boolean notValidKey(String key) {
        if (key == null || key.isBlank() || key.isEmpty()) {
            return true;
        }
        return !CODE_PATTERN.matcher(key).matches();
    }

    private boolean notValidValues(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        for (String value : values) {
            if (!CODE_PATTERN.matcher(value).matches()) {
                return true;
            }
        }

        return false;
    }

}

