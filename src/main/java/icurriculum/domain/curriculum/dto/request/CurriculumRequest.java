package icurriculum.domain.curriculum.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CurriculumRequest(

/*        @Valid
        @NotNull(message = "decider는 필수 값입니다.")
        DeciderRequest decider,*/

        @NotNull(message = "LastEditor는 필수 값입니다.")
        String lastEditor,

        @Valid
        @NotNull(message = "Core는 필수 값입니다.")
        CoreRequest core,

        @Valid
        @NotNull(message = "SwAi는 필수 값입니다.")
        SwAiRequest swAi,

        @Valid
        @NotNull(message = "Creativity는 필수 값입니다.")
        CreativityRequest creativity,

        @Valid
        @NotNull(message = "MajorRequired는 필수 값입니다.")
        MajorRequiredRequest majorRequired,

        @Valid
        @NotNull(message = "MajorSelect는 필수 값입니다.")
        MajorSelectRequest majorSelect,

        @Valid
        @NotNull(message = "GeneralRequired는 필수 값입니다.")
        GeneralRequiredRequest generalRequired,

        @Valid
        @NotNull(message = "RequiredCredit는 필수 값입니다.")
        RequiredCreditRequest requiredCredit,

        @Valid
        @NotNull(message = "AlternativeCourse는 필수 값입니다.")
        AlternativeCourseRequest alternativeCourse
) {

}
