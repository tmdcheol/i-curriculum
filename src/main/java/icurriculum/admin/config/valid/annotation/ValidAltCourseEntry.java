package icurriculum.admin.config.valid.annotation;

import icurriculum.admin.config.valid.validator.AltCourseEntryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AltCourseEntryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAltCourseEntry {

    String message() default "{Code}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
