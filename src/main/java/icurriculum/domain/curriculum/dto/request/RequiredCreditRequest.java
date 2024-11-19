package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RequiredCreditRequest(
        @JsonProperty("총필요학점")
        @NotNull(message = "총 필요 학점은 필수 값입니다.")
        Integer totalNeedCredit,

        @JsonProperty("단일전공_필요학점")
        @NotNull(message = "단일 전공 필요 학점은 필수 값입니다.")
        Integer singleNeedCredit,

        @JsonProperty("복수_연계_융합_전공_필요학점")
        @NotNull(message = "복수, 연계, 융합 전공 필요 학점은 필수 값입니다.")
        Integer secondNeedCredit,

        @JsonProperty("부전공_필요학점")
        @NotNull(message = "부전공 필요 학점은 필수 값입니다.")
        Integer minorNeedCredit
) {

}
