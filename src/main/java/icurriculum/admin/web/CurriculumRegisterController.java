package icurriculum.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icurriculum.admin.web.form.LastEditor;
import icurriculum.admin.web.form.RegisterForm;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.dto.request.CurriculumRequest;
import icurriculum.domain.curriculum.service.AdminCurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class CurriculumRegisterController {

    private final AdminCurriculumService curriculumService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        initModel(model);
        model.addAttribute("registerForm", new RegisterForm());
        return "curriculums/createForm";
    }

    /**
     * 화면에서 버튼 클릭하면 자바에서 한번 있는지 검증하고, 이미 있으면 존재하는 거라고 막아버리기
     *
     * <p>없다면, 크롤러 api를 호출
     *
     * <p>크롤러 api 호출되면, 그 값을 받아서 저장하기
     */
    @PostMapping("/register")
    public String registerCurriculum(
            @RequestParam(required = false) String majorType,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) Integer joinYear,
            @RequestParam(required = false) String curriculumData,
            @Valid @ModelAttribute RegisterForm registerForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            initModel(model);
            log.info("Form validation error: {}", result);
            return "curriculums/createForm";
        }

        // Handle curriculum registration logic
        try {
            if (curriculumData != null) {
                // JSON parsing to CurriculumRequest object if curriculumData is provided
                ObjectMapper objectMapper = new ObjectMapper();
                CurriculumRequest curriculumRequest = objectMapper.readValue(curriculumData,
                        CurriculumRequest.class);

                // CurriculumRequest to Curriculum object conversion
                Curriculum curriculum = icurriculum.domain.curriculum.dto.CurriculumConverter.to(
                        majorType, departmentName, joinYear, curriculumRequest
                );
                log.info("Parsed curriculum: {}", curriculum);

                // Service call for creating the curriculum
                String message = curriculumService.create(majorType, departmentName, joinYear,
                        curriculum);
                log.info("Curriculum service message: {}", message);

                // Redirect based on service response
                if ("duplicate".equals(message)) {
                    redirectAttributes.addFlashAttribute("message", "커리큘럼 등록 실패: 중복된 데이터입니다.");
                    redirectAttributes.addFlashAttribute("isDuplicated", true);
                    redirectAttributes.addFlashAttribute("redirectUrl", "/admin");
                    return "redirect:/admin/result/failure";
                }

                redirectAttributes.addFlashAttribute("message", "커리큘럼 등록 성공");
                redirectAttributes.addFlashAttribute("redirectUrl", "/admin");
                return "redirect:/admin/result/success";
            } else {
                // Handle case when curriculumData is not provided
                log.warn("No curriculum data provided.");
                redirectAttributes.addFlashAttribute("message", "커리큘럼 등록 실패: 데이터가 부족합니다.");
                redirectAttributes.addFlashAttribute("redirectUrl", "/admin/result/failure");
                return "redirect:/admin/result/failure";
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing error: ", e);
            redirectAttributes.addFlashAttribute("message", "커리큘럼 등록 실패: 데이터 처리 중 오류 발생");
            redirectAttributes.addFlashAttribute("redirectUrl", "/admin");
            return "redirect:/admin/result/failure";
        }
    }

    private void initModel(Model model) {
        model.addAttribute("departmentNames", DepartmentName.values());
        model.addAttribute("majorTypes", MajorType.values());
        model.addAttribute(
                "joinYears", List.of("2018", "2019", "2020", "2021", "2022", "2023", "2024"));
        model.addAttribute("lastEditors", LastEditor.values());
    }

}
