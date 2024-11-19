package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/*
 * 필수이수학점
 */
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class RequiredCredit {

    @JsonProperty("총필요학점")
    private Integer totalNeedCredit;

    @JsonProperty("단일전공_필요학점")
    private Integer singleNeedCredit;

    @JsonProperty("복수_연계_융합_전공_필요학점")
    private Integer secondNeedCredit;

    @JsonProperty("부전공_필요학점")
    private Integer minorNeedCredit;

    @Builder
    private RequiredCredit(
            @NonNull Integer totalNeedCredit,
            @NonNull Integer singleNeedCredit,
            @NonNull Integer secondNeedCredit,
            @NonNull Integer minorNeedCredit
    ) {
        this.totalNeedCredit = totalNeedCredit;
        this.singleNeedCredit = singleNeedCredit;
        this.secondNeedCredit = secondNeedCredit;
        this.minorNeedCredit = minorNeedCredit;
    }
}

/*
 * {
 *   "총필요학점": 130,
 *   "단일전공_필요학점": 65,
 *   "복수_연계_융합_전공_필요학점": 39,
 *   "부전공_필요학점": 21
 * }
 */
