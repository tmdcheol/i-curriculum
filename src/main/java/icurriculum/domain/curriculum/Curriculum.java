package icurriculum.domain.curriculum;

import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseMongoEntity;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "curriculums")
@NoArgsConstructor(access = PROTECTED)
@CompoundIndexes({
        @CompoundIndex(
                name = "uniqueCurriculumDecider",
                def = "{'decider.majorType': 1, 'decider.departmentName': 1, 'decider.joinYear': 1}",
                unique = true)
})
@Getter
@ToString
public class Curriculum extends BaseMongoEntity {

    @Id
    private String id;

    private CurriculumDecider decider;
    private String lastEditor;
    private Core core;
    private SwAi swAi;
    private Creativity creativity;
    private MajorRequired majorRequired;
    private MajorSelect majorSelect;
    private GeneralRequired generalRequired;
    private RequiredCredit requiredCredit;
    private AlternativeCourse alternativeCourse;

    @Builder
    private Curriculum(
            @NonNull CurriculumDecider decider,
            @NonNull String lastEditor,
            @NonNull Core core,
            @NonNull SwAi swAi,
            @NonNull Creativity creativity,
            @NonNull MajorRequired majorRequired,
            @NonNull MajorSelect majorSelect,
            @NonNull GeneralRequired generalRequired,
            @NonNull RequiredCredit requiredCredit,
            @NonNull AlternativeCourse alternativeCourse
    ) {
        this.decider = decider;
        this.lastEditor = lastEditor;
        this.core = core;
        this.swAi = swAi;
        this.creativity = creativity;
        this.majorRequired = majorRequired;
        this.majorSelect = majorSelect;
        this.generalRequired = generalRequired;
        this.requiredCredit = requiredCredit;
        this.alternativeCourse = alternativeCourse;
    }

    public void update(Curriculum newCurriculum) {
        this.lastEditor = newCurriculum.getLastEditor();
        this.core = newCurriculum.getCore();
        this.swAi = newCurriculum.getSwAi();
        this.creativity = newCurriculum.getCreativity();
        this.majorRequired = newCurriculum.getMajorRequired();
        this.majorSelect = newCurriculum.getMajorSelect();
        this.generalRequired = newCurriculum.getGeneralRequired();
        this.requiredCredit = newCurriculum.getRequiredCredit();
        this.alternativeCourse = newCurriculum.getAlternativeCourse();
    }

    public void updateRequiredCredit(RequiredCredit requiredCredit) {
        this.requiredCredit = requiredCredit;
    }

    public void updateMajorRequired(MajorRequired majorRequired) {
        this.majorRequired = majorRequired;
    }

    public void updateMajorSelect(MajorSelect majorSelect) {
        this.majorSelect = majorSelect;
    }

    public void updateGeneralRequired(GeneralRequired generalRequired) {
        this.generalRequired = generalRequired;
    }

    public void updateSwAi(SwAi swAi) {
        this.swAi = swAi;
    }

    public void updateCore(Core core) {
        this.core = core;
    }

    public void updateCreativity(Creativity creativity) {
        this.creativity = creativity;
    }

    public void updateAlternativeCourse(AlternativeCourse alternativeCourse) {
        this.alternativeCourse = alternativeCourse;
    }

}
