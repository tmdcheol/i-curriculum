package icurriculum.domain.graduation.processor.swai.strategy;

import icurriculum.domain.course.Course;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.graduation.processor.dto.ProcessorRequest;
import icurriculum.domain.graduation.processor.dto.ProcessorResponse;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.global.util.GraduationUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class CommonSwAiStrategy implements SwAiStrategy {

    /*
     * [logic]
     * 1. 영역 대체 과목 화인
     *      영역 대체의 경우, Take LinkedList에서 삭제되지 않고, response 계산만 수행
     *      이유: 대체과목은 다른영역(ex.교양필수)에도 포함되기 때문에 교양필수에서 계산되어야 한다.
     * 2. Category가 SW_AI인 take는 이수학점을 추가하고 LinkedList에서 삭제한다.
     */
    @Override
    public ProcessorResponse.SwAiDTO execute(
        ProcessorRequest.SwAiDTO request,
        LinkedList<Take> allTakeList
    ) {
        ProcessorResponse.SwAiDTO response = new ProcessorResponse.SwAiDTO();

        // 영역 대체 과목
        Set<Course> areaAltCourseSet = handleAreaAlternative(allTakeList, request.swAi(),
            request.alternativeCourse(), response);

        return createResponse(request.swAi(), allTakeList, areaAltCourseSet, response);
    }

    /*
     * [영역 대체 logic]
     * 1. 영역 대체 과목은 take의 category와 관련 없이 response 계산 수행
     * 2. Take LinkedList 내에서 제외되지 않는다.
     * 3. return Set<Course>
            영역 대체된 과목을 담아 리턴한다.
     *      이 후 작업에서 Set에 담겨있는 과목이면 건너뛴다.
     */
    private Set<Course> handleAreaAlternative(
        LinkedList<Take> allTakeList,
        SwAi swAi,
        AlternativeCourse alternativeCourse,
        ProcessorResponse.SwAiDTO response
    ) {
        Set<Course> areaAltCourseSet = new HashSet<>();

        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isApproved(take, swAi.getAreaAlternativeCodeSet())) {
                response.update(take, iterator, true);
                areaAltCourseSet.add(take.getEffectiveCourse());
                continue;
            }

            if (GraduationUtils.isCodeAlternative(
                take,
                swAi.getAreaAlternativeCodeSet(),
                alternativeCourse)
            ) {
                response.update(take, iterator, true);
                areaAltCourseSet.add(take.getEffectiveCourse());
            }
        }

        return areaAltCourseSet;
    }

    private ProcessorResponse.SwAiDTO createResponse(
        SwAi swAi,
        LinkedList<Take> allTakeList,
        Set<Course> areaAltCourseSet,
        ProcessorResponse.SwAiDTO response
    ) {
        Iterator<Take> iterator = allTakeList.iterator();
        while (iterator.hasNext()) {
            Take take = iterator.next();

            if (GraduationUtils.isAlreadyCheckedByAreaAlt(take, areaAltCourseSet)) {
                continue;
            }

            if (GraduationUtils.isApprovedCategory(take, Category.SW_AI)) {
                response.update(take, iterator, false);
            }
        }

        response.setRequiredCredit(swAi);
        response.checkIsClear();

        return response;
    }

}
