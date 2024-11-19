package icurriculum.admin.web.converter;

import static lombok.AccessLevel.PRIVATE;

import icurriculum.admin.web.form.detail.AltCourseEntry;
import icurriculum.admin.web.form.detail.AlternativeCourseForm;
import icurriculum.admin.web.form.detail.CoreForm;
import icurriculum.admin.web.form.detail.CreativityForm;
import icurriculum.admin.web.form.detail.Entry;
import icurriculum.admin.web.form.detail.GeneralRequiredForm;
import icurriculum.admin.web.form.detail.MajorRequiredForm;
import icurriculum.admin.web.form.detail.MajorSelectForm;
import icurriculum.admin.web.form.detail.RequiredCreditForm;
import icurriculum.admin.web.form.detail.SwAiForm;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CurriculumConverter {

    /*
     * Curriculum Converter
     */
/*
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
*/


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
    public static Core to(CoreForm form) {
        Set<Category> requiredAreaSet =
                form.requiredAreaSet() != null ?
                        form.requiredAreaSet().stream()
                                .map(Category::to)
                                .collect(Collectors.toSet()) :
                        Set.of();

        Map<Category, Set<String>> areaDeterminedCodeMap = new HashMap<>();
        if (form.areaDeterminedCodeSetOne() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양1, form.areaDeterminedCodeSetOne());
        }
        if (form.areaDeterminedCodeSetTwo() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양2, form.areaDeterminedCodeSetTwo());
        }
        if (form.areaDeterminedCodeSetThree() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양3, form.areaDeterminedCodeSetThree());
        }
        if (form.areaDeterminedCodeSetFour() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양4, form.areaDeterminedCodeSetFour());
        }
        if (form.areaDeterminedCodeSetFive() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양5, form.areaDeterminedCodeSetFive());
        }
        if (form.areaDeterminedCodeSetSix() != null) {
            areaDeterminedCodeMap.put(Category.핵심교양6, form.areaDeterminedCodeSetSix());
        }

        Map<Category, Set<String>> areaAlternativeCodeMap = new HashMap<>();
        if (form.areaAlternativeCodeSetOne() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양1, form.areaAlternativeCodeSetOne());
        }
        if (form.areaAlternativeCodeSetTwo() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양2, form.areaAlternativeCodeSetTwo());
        }
        if (form.areaAlternativeCodeSetThree() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양3, form.areaAlternativeCodeSetThree());
        }
        if (form.areaAlternativeCodeSetFour() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양4, form.areaAlternativeCodeSetFour());
        }
        if (form.areaAlternativeCodeSetFive() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양5, form.areaAlternativeCodeSetFive());
        }
        if (form.areaAlternativeCodeSetSix() != null) {
            areaAlternativeCodeMap.put(Category.핵심교양6, form.areaAlternativeCodeSetSix());
        }

        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return Core.builder()
                .isAreaFixed(form.isAreaFixed())
                .requiredCredit(form.requiredCredit())
                .requiredAreaSet(requiredAreaSet)
                .areaDeterminedCodeMap(areaDeterminedCodeMap)
                .areaAlternativeCodeMap(areaAlternativeCodeMap)
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    public static CoreForm to(Core core) {
        Set<String> requiredAreaSet = core.getRequiredAreaSet().stream()
                .map(Category::name)
                .collect(Collectors.toSet());

        return new CoreForm(
                core.getIsAreaFixed(),
                core.getRequiredCredit(),
                requiredAreaSet,
                core.getAreaDeterminedCodeSet(Category.핵심교양1),
                core.getAreaDeterminedCodeSet(Category.핵심교양2),
                core.getAreaDeterminedCodeSet(Category.핵심교양3),
                core.getAreaDeterminedCodeSet(Category.핵심교양4),
                core.getAreaDeterminedCodeSet(Category.핵심교양5),
                core.getAreaDeterminedCodeSet(Category.핵심교양6),

                core.getAreaAlternativeCodeSet(Category.핵심교양1),
                core.getAreaAlternativeCodeSet(Category.핵심교양2),
                core.getAreaAlternativeCodeSet(Category.핵심교양3),
                core.getAreaAlternativeCodeSet(Category.핵심교양4),
                core.getAreaAlternativeCodeSet(Category.핵심교양5),
                core.getAreaAlternativeCodeSet(Category.핵심교양6),
                core.getAdditionalInfoForFrontend()
        );
    }

    /*
     * SwAi Converter
     */
    public static SwAi to(SwAiForm form) {
        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return SwAi.builder()
                .approvedCodeSet(form.approvedCodeSet())
                .areaAlternativeCodeSet(form.areaAlternativeCodeSet())
                .requiredCredit(form.requiredCredit())
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    public static SwAiForm to(SwAi swAi) {
        return new SwAiForm(
                swAi.getApprovedCodeSet(),
                swAi.getAreaAlternativeCodeSet(),
                swAi.getRequiredCredit(),
                swAi.getAdditionalInfoForFrontend()
        );
    }

    /*
     * Creativity Converter
     */
    public static CreativityForm to(Creativity creativity) {
        return new CreativityForm(
                creativity.getApprovedCodeSet(),
                creativity.getRequiredCredit(),
                creativity.getAdditionalInfoForFrontend()
        );
    }

    public static Creativity to(CreativityForm form) {
        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return Creativity.builder()
                .approvedCodeSet(form.approvedCodeSet())
                .requiredCredit(form.requiredCredit())
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    /*
     * MajorSelect Converter
     */
    public static MajorSelect to(MajorSelectForm form) {
        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return MajorSelect.builder()
                .codeSet(form.codeSet())
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    public static MajorSelectForm to(MajorSelect majorSelect) {
        return new MajorSelectForm(
                majorSelect.getCodeSet(),
                majorSelect.getAdditionalInfoForFrontend()
        );
    }

    /*
     * MajorRequired Converter
     */
    public static MajorRequired to(MajorRequiredForm form) {
        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return MajorRequired.builder()
                .codeSet(form.codeSet())
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    public static MajorRequiredForm to(MajorRequired majorRequired) {
        return new MajorRequiredForm(
                majorRequired.getCodeSet(),
                majorRequired.getAdditionalInfoForFrontend()
        );
    }

    /*
     * GeneralRequired Converter
     */
    public static GeneralRequired to(GeneralRequiredForm form) {
        Map<String, String> additionalInfoMap = new HashMap<>();
        List<Entry> entries = form.additionalInfos();
        for (Entry entry : entries) {
            additionalInfoMap.put(entry.getKey(), entry.getValue());
        }

        return GeneralRequired.builder()
                .codeSet(form.codeSet())
                .additionalInfoMap(additionalInfoMap)
                .build();
    }

    public static GeneralRequiredForm to(GeneralRequired generalRequired) {
        return new GeneralRequiredForm(
                generalRequired.getCodeSet(),
                generalRequired.getAdditionalInfoForFrontend()
        );
    }

    /*
     * RequiredCredit Converter
     */
    public static RequiredCredit to(RequiredCreditForm form) {
        return RequiredCredit.builder()
                .totalNeedCredit(form.getTotalNeedCredit())
                .singleNeedCredit(form.getSingleNeedCredit())
                .secondNeedCredit(form.getSecondNeedCredit())
                .minorNeedCredit(form.getMinorNeedCredit())
                .build();
    }

    /*
     * AlternativeCourse Converter
     */

    public static AlternativeCourseForm to(AlternativeCourse alternativeCourse) {
        List<AltCourseEntry> codes = new ArrayList<>();

        Set<String> codeKeySet = alternativeCourse.getCodeKeySet();
        for (String code : codeKeySet) {
            Set<String> alternativeCodeSet = alternativeCourse.getAlternativeCodeSet(code);
            codes.add(new AltCourseEntry(code, alternativeCodeSet));
        }

        return new AlternativeCourseForm(codes);
    }

    public static AlternativeCourse to(AlternativeCourseForm form) {
        Map<String, Set<String>> alternativeCourseCodeMap = new HashMap<>();

        for (AltCourseEntry entry : form.codes()) {
            alternativeCourseCodeMap.put(entry.getKey(), entry.getValues());
        }

        return AlternativeCourse.builder()
                .alternativeCourseCodeMap(alternativeCourseCodeMap)
                .build();
    }

}
