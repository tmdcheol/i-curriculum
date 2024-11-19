package icurriculum.domain.curriculum.dto.request;

import jakarta.validation.constraints.NotNull;

public record DeciderRequest(
        @NotNull(message = "MajorType은 필수 값입니다.")
        String majorType,

        @NotNull(message = "DepartmentName은 필수 값입니다.")
        String departmentName,

        @NotNull(message = "JoinYear는 필수 값입니다.")
        Integer joinYear
) {

}
