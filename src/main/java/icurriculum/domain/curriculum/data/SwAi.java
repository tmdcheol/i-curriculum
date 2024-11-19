package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icurriculum.admin.web.form.detail.Entry;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/*
 * [SW_AI, 일반교양7]
 *
 * 인정과목: SW_AI로 인정받을 수 있는 과목코드들을 넣는다 (보통 프런티어 대학 기준)
 *
 * - 영역대체과목: 해당과목을 들었으면 영역을 이수한 것으로 인정되는 경우이다. 동시에 해당과목이 다른 영역에도 영향을 줄 수 있다.
 * - ex. 전기공학과 ("ACE2105", "ACE2103","ACE1307") => 교양필수
 *
 * - 추가정보 저장 가능 (additionalInfoMap)
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class SwAi {

    @Getter
    @JsonProperty("인정과목")
    private Set<String> approvedCodeSet = new HashSet<>();

    @Getter
    @JsonProperty("영역대체과목")
    private Set<String> areaAlternativeCodeSet = new HashSet<>();

    @Getter
    @JsonProperty("필요학점")
    private Integer requiredCredit;

    @JsonProperty("추가정보")
    private Map<String, String> additionalInfoMap = new HashMap<>();

    @Builder
    private SwAi(
            Set<String> approvedCodeSet,
            Set<String> areaAlternativeCodeSet,
            @NonNull Integer requiredCredit,
            Map<String, String> additionalInfoMap
    ) {
        this.approvedCodeSet = (approvedCodeSet != null) ?
                approvedCodeSet : new HashSet<>();

        this.areaAlternativeCodeSet = (areaAlternativeCodeSet != null) ?
                areaAlternativeCodeSet : new HashSet<>();

        this.requiredCredit = requiredCredit;

        this.additionalInfoMap = (additionalInfoMap != null) ?
                additionalInfoMap : new HashMap<>();

        validate();
    }

    public void validate() {
        if (requiredCredit.equals(0) && !approvedCodeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.SW_AI_INVALID_DATA, this);
        }
        if (!requiredCredit.equals(0) && approvedCodeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.SW_AI_INVALID_DATA, this);
        }
    }

    public <T> Optional<T> getAdditionalInfo(String key, Class<T> targetType) {
        String json = additionalInfoMap.get(key);
        if (json == null || json.isBlank()) {
            throw new GeneralException(ErrorStatus.EMPTY_ADDITIONAL_INFO_FAILURE, key);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            T value = objectMapper.readValue(json, targetType);
            return Optional.of(value);
        } catch (JsonProcessingException e) {
            throw new GeneralException(ErrorStatus.DESERIALIZATION_FAILURE, this, e);
        }
    }

    public List<Entry> getAdditionalInfoForFrontend() {
        List<Entry> entries = new ArrayList<>();
        for (String key : additionalInfoMap.keySet()) {
            String jsonValue = additionalInfoMap.get(key);
            entries.add(new Entry(key, jsonValue));
        }
        return entries;
    }

}

/*
 * {
 *   "인정과목": ["CEE1101", ...],
 *   "영역대체과목": ["ACE2105", "ACE2103", "ACE1307"],
 *   "필요학점": 3,
 *   "추가정보": {}
 * }
 */
