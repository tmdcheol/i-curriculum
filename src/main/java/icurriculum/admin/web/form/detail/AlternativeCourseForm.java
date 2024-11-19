package icurriculum.admin.web.form.detail;

import icurriculum.admin.config.valid.annotation.ValidAltCourseEntry;
import java.util.ArrayList;
import java.util.List;

public record AlternativeCourseForm(
        List<@ValidAltCourseEntry AltCourseEntry> codes
) {

    public AlternativeCourseForm {
        if (codes == null) {
            codes = new ArrayList<>();
        }
    }

}
