package icurriculum.domain.curriculum.controller;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.dto.CurriculumConverter;
import icurriculum.domain.curriculum.dto.request.CurriculumRequest;
import icurriculum.domain.curriculum.service.AdminCurriculumService;
import icurriculum.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class AdminCurriculumCommandController {

    private final AdminCurriculumService service;


    private static final String UPDATE_SUCCESS_MESSAGE = "성공적으로 커리큘럼이 업데이트되었습니다.";
    private static final String DELETE_SUCCESS_MESSAGE = "성공적으로 커리큘럼 삭제";


    /* Todo
     * 요청 값 DeciderRequest 로 바꾸기
     */
    @GetMapping("/load")
    public ApiResponse<Curriculum> get(
            @RequestParam String majorType,
            @RequestParam String departmentName,
            @RequestParam Integer joinYear
    ) {
        Curriculum curriculum = service.getCurriculum(majorType, departmentName, joinYear);
        return ApiResponse.onSuccess(curriculum);
    }

    /* Todo
     * 요청 값 DeciderRequest 로 바꾸기
     * 굳이 CurriculumRequest에 이미 Decider 값이 있는데, 요청파라미터로 3개 값 필요?
     * 응답 값 메세지 형태에서 ApiResponse.OK 로 바꾸기
     */
    @PostMapping("/save")
    public ApiResponse<String> create(
            @RequestParam String majorType,
            @RequestParam String departmentName,
            @RequestParam Integer joinYear,
            @Valid @RequestBody CurriculumRequest curriculumRequest
    ) {
        Curriculum curriculum = CurriculumConverter.to(
                majorType, departmentName,
                joinYear, curriculumRequest
        );
        String message = service.create(majorType, departmentName, joinYear, curriculum);

        return ApiResponse.onSuccess(message);
    }

    /* Todo
     * 요청 값 DeciderRequest 로 바꾸기
     * 굳이 CurriculumRequest에 이미 Decider 값이 있는데, 요청파라미터로 3개 값 필요?
     * 응답 값 메세지 형태에서 ApiResponse.OK 로 바꾸기
     */
    @PostMapping("/update")
    public ApiResponse<String> updateCurriculum(
            @RequestParam String majorType,
            @RequestParam String departmentName,
            @RequestParam Integer joinYear,
            @Valid @RequestBody CurriculumRequest curriculumRequest
    ) {
        Curriculum updatedCurriculum = CurriculumConverter.to(
                majorType, departmentName,
                joinYear, curriculumRequest
        );
        service.update(majorType, departmentName, joinYear, updatedCurriculum);

        return ApiResponse.onSuccess(UPDATE_SUCCESS_MESSAGE);
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deleteCurriculum(
            @RequestParam String majorType,
            @RequestParam String departmentName,
            @RequestParam Integer joinYear
    ) {
        service.delete(majorType, departmentName, joinYear);
        return ApiResponse.onSuccess(DELETE_SUCCESS_MESSAGE);
    }
}
