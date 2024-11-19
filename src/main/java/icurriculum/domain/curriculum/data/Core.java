package icurriculum.domain.curriculum.data;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icurriculum.admin.web.form.detail.Entry;
import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.GraduationUtils;
import java.util.ArrayList;
import java.util.Collections;
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
 * [핵심교양]
 *
 * - 필수영역: 영역_지정여부가 false일 경우 비어있는 Set을 넣는다.
 *
 * - 영역별_지정과목: 해당과목만 꼭 들어야하는 경우
 *      ex. 핵심교양1 - 공윤토
 *
 * - 영역별_대체과목: 해당과목을 들었으면 영역을 이수한 것으로 인정되는 경우이다. 동시에 해당과목이 다른 영역에도 영향을 줄 수 있다.
 *      ex. 핵심교양6: 파이썬과머신러닝(전공선택), 수치해석(전공선택)
 *
 * - 추가정보 저장 가능 (additionalInfoMap)
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class Core {

    @Getter
    @JsonProperty("영역_지정여부")
    private Boolean isAreaFixed;

    @Getter
    @JsonProperty("요구학점")
    private Integer requiredCredit;

    @Getter
    @JsonProperty("필수영역")
    private Set<Category> requiredAreaSet = new HashSet<>();

    @JsonProperty("영역별_지정과목")
    private Map<Category, Set<String>> areaDeterminedCodeMap = new HashMap<>();

    @JsonProperty("영역별_대체과목")
    private Map<Category, Set<String>> areaAlternativeCodeMap = new HashMap<>();

    @JsonProperty("추가정보")
    private Map<String, String> additionalInfoMap = new HashMap<>();

    @Builder
    private Core(
            @NonNull Boolean isAreaFixed,
            @NonNull Integer requiredCredit,
            Set<Category> requiredAreaSet,
            Map<Category, Set<String>> areaDeterminedCodeMap,
            Map<Category, Set<String>> areaAlternativeCodeMap,
            Map<String, String> additionalInfoMap
    ) {
        this.isAreaFixed = isAreaFixed;

        this.requiredCredit = requiredCredit;

        this.requiredAreaSet = (requiredAreaSet != null) ?
                requiredAreaSet : new HashSet<>();

        this.areaDeterminedCodeMap = (areaDeterminedCodeMap != null) ?
                areaDeterminedCodeMap : new HashMap<>();

        this.areaAlternativeCodeMap = (areaAlternativeCodeMap != null) ?
                areaAlternativeCodeMap : new HashMap<>();

        this.additionalInfoMap = (additionalInfoMap != null) ?
                additionalInfoMap : new HashMap<>();

        validate();
    }

    public Set<String> getAreaDeterminedCodeSet(Category category) {
        validateCategory(category);
        return areaDeterminedCodeMap.getOrDefault(category, Collections.emptySet());
    }

    public Set<String> getAreaAlternativeCodeSet(Category category) {
        validateCategory(category);
        return areaAlternativeCodeMap.getOrDefault(category, Collections.emptySet());
    }

    public void validate() {
        if (!isAreaFixed && !requiredAreaSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CORE_INVALID_DATA, this);
        }
        if (isAreaFixed && requiredAreaSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CORE_INVALID_DATA, this);
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

    private void validateCategory(Category category) {
        if (GraduationUtils.CORE_CATEGORYSET.contains(category)) {
            return;
        }

        throw new GeneralException(ErrorStatus.CORE_NOT_VALID_CATEGORY, this);
    }

}

/*
 * {
 *   "영역_지정여부": true,
 *   "요구학점": 9,
 *   "필수영역": ["핵심교양1", "핵심교양2", "핵심교양4"],
 *   "영역별_지정과목": {
 *     "핵심교양1": ["GED2101"]
 *   },
 *   "영역별_대체과목": {
 *     "핵심교양6": ["ACE1301", "ACE1306"]
 *   }
 *   "추가정보": {}
 * }
 */

/*
 * {
 *   "영역_지정여부": false,
 *   "요구학점": 9,
 *   "필수영역": {},
 *   "영역별_지정과목": {},
 *   "영역별_대체과목": {}
 *   "추가정보": {}
 * }
 */