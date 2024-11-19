package icurriculum.domain.curriculum.dto;

import static lombok.AccessLevel.PRIVATE;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.curriculum.dto.request.AlternativeCourseRequest;
import icurriculum.domain.curriculum.dto.request.CoreRequest;
import icurriculum.domain.curriculum.dto.request.CreativityRequest;
import icurriculum.domain.curriculum.dto.request.CurriculumRequest;
import icurriculum.domain.curriculum.dto.request.GeneralRequiredRequest;
import icurriculum.domain.curriculum.dto.request.MajorRequiredRequest;
import icurriculum.domain.curriculum.dto.request.MajorSelectRequest;
import icurriculum.domain.curriculum.dto.request.RequiredCreditRequest;
import icurriculum.domain.curriculum.dto.request.SwAiRequest;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Category;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CurriculumConverter {

    /*
     * Curriculum Converter
     */
    public static Curriculum to(
            String majorType,
            String departmentName,
            Integer joinYear,
            CurriculumRequest request
    ) {
        return Curriculum.builder()
                .decider(to(majorType, departmentName, joinYear))
                .lastEditor(request.lastEditor())
                .core(to(request.core()))
                .swAi(to(request.swAi()))
                .creativity(to(request.creativity()))
                .majorRequired(to(request.majorRequired()))
                .majorSelect(to(request.majorSelect()))
                .generalRequired(to(request.generalRequired()))
                .requiredCredit(to(request.requiredCredit()))
                .alternativeCourse(to(request.alternativeCourse())).build();
    }


    /*
     * Decider Converter
     */
    private static CurriculumDecider to(
            String majorType,
            String departmentName,
            Integer joinYear
    ) {
        return CurriculumDecider.builder()
                .majorType(MajorType.valueOf(majorType))
                .departmentName(DepartmentName.valueOf(departmentName))
                .joinYear(joinYear)
                .build();
    }

    /*
     * Core Converter
     */
    private static Core to(CoreRequest request) {
        Set<Category> requiredAreaSet =
                request.requiredAreaSet() != null ?
                        request.requiredAreaSet().stream()
                                .map(Category::to)
                                .collect(Collectors.toSet()) :
                        Set.of();

        Map<Category, Set<String>> areaDeterminedCodeMap =
                request.areaDeterminedCodeMap() != null ?
                        request.areaDeterminedCodeMap().entrySet().stream()
                                .collect(Collectors.toMap(entry -> Category.to(entry.getKey()),
                                        Map.Entry::getValue)) :
                        Map.of();

        Map<Category, Set<String>> areaAlternativeCodeMap =
                request.areaAlternativeCodeMap() != null ? request.areaAlternativeCodeMap()
                        .entrySet().stream().collect(
                                Collectors.toMap(entry -> Category.to(entry.getKey()),
                                        Map.Entry::getValue)) :
                        Map.of();

        return Core.builder()
                .isAreaFixed(request.isAreaFixed())
                .requiredCredit(request.requiredCredit())
                .requiredAreaSet(requiredAreaSet)
                .areaDeterminedCodeMap(areaDeterminedCodeMap)
                .areaAlternativeCodeMap(areaAlternativeCodeMap)
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * SwAi Converter
     */
    private static SwAi to(SwAiRequest request) {
        return SwAi.builder()
                .approvedCodeSet(request.approvedCodeSet())
                .areaAlternativeCodeSet(request.areaAlternativeCodeSet())
                .requiredCredit(request.requiredCredit())
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * Creativity Converter
     */
    private static Creativity to(CreativityRequest request) {
        return Creativity.builder()
                .approvedCodeSet(request.approvedCodeSet())
                .requiredCredit(request.requiredCredit())
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * MajorSelect Converter
     */
    private static MajorRequired to(MajorRequiredRequest request) {
        return MajorRequired.builder()
                .codeSet(request.codeSet())
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * MajorRequired Converter
     */
    private static MajorSelect to(MajorSelectRequest request) {
        return MajorSelect.builder()
                .codeSet(request.codeSet())
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * GeneralRequired Converter
     */
    private static GeneralRequired to(GeneralRequiredRequest request) {
        return GeneralRequired.builder()
                .codeSet(request.codeSet())
                .additionalInfoMap(request.additionalInfoMap())
                .build();
    }

    /*
     * RequiredCredit Converter
     */
    private static RequiredCredit to(RequiredCreditRequest request) {
        return RequiredCredit.builder()
                .totalNeedCredit(request.totalNeedCredit())
                .singleNeedCredit(request.singleNeedCredit())
                .secondNeedCredit(request.secondNeedCredit())
                .minorNeedCredit(request.minorNeedCredit())
                .build();
    }

    /*
     * AlternativeCourse Converter
     */
    private static AlternativeCourse to(AlternativeCourseRequest request) {
        return AlternativeCourse.builder()
                .alternativeCourseCodeMap(request.alternativeCourseCodeMap())
                .build();
    }

}
