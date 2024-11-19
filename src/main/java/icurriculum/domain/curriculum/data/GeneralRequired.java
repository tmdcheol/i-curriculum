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
import lombok.ToString;

/*
 * [교양필수]
 *
 * - 크롤러로 가져오는 데이터, 필요 시 수정해야 함
 * - 추가정보 저장 가능 (additionalInfoMap)
 */

@NoArgsConstructor(access = PROTECTED)
@ToString
public class GeneralRequired {

    @Getter
    @JsonProperty("과목코드")
    private Set<String> codeSet = new HashSet<>();

    @JsonProperty("추가정보")
    private Map<String, String> additionalInfoMap = new HashMap<>();

    @Builder
    private GeneralRequired(
            Set<String> codeSet,
            Map<String, String> additionalInfoMap
    ) {
        this.codeSet = (codeSet != null) ?
                codeSet : new HashSet<>();

        this.additionalInfoMap = (additionalInfoMap != null) ?
                additionalInfoMap : new HashMap<>();

        validate();
    }

    public void validate() {
        if (codeSet.isEmpty()) {
            throw new GeneralException(ErrorStatus.CURRICULUM_MISSING_VALUE, this);
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
