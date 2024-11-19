package icurriculum.admin.web.form.detail;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RequiredCreditForm {

    @NotNull
    @Range(min = 0, max = 200)
    private Integer totalNeedCredit;

    @NotNull
    @Range(min = 0, max = 100)
    Integer singleNeedCredit;

    @NotNull
    @Range(min = 0, max = 100)
    Integer secondNeedCredit;

    @NotNull
    @Range(min = 0, max = 100)
    Integer minorNeedCredit;

}

