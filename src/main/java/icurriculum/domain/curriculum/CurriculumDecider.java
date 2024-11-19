package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class CurriculumDecider {

    private MajorType majorType;

    private DepartmentName departmentName;

    private Integer joinYear;

    @Builder
    private CurriculumDecider(
            @NonNull MajorType majorType,
            @NonNull DepartmentName departmentName,
            @NonNull Integer joinYear
    ) {
        this.majorType = majorType;
        this.departmentName = departmentName;
        this.joinYear = joinYear;
    }

}
