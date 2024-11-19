package icurriculum.admin.web;

import icurriculum.admin.service.AdminService;
import icurriculum.admin.web.converter.CurriculumConverter;
import icurriculum.admin.web.form.LastEditor;
import icurriculum.admin.web.form.ModifyForm;
import icurriculum.admin.web.form.ModifyParam;
import icurriculum.admin.web.form.detail.AlternativeCourseForm;
import icurriculum.admin.web.form.detail.CoreForm;
import icurriculum.admin.web.form.detail.CreativityForm;
import icurriculum.admin.web.form.detail.GeneralRequiredForm;
import icurriculum.admin.web.form.detail.MajorRequiredForm;
import icurriculum.admin.web.form.detail.MajorSelectForm;
import icurriculum.admin.web.form.detail.RequiredCreditForm;
import icurriculum.admin.web.form.detail.SwAiForm;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class CurriculumModifyController {

    private final AdminService service;

    @GetMapping("/modify")
    public String modifyForm(Model model) {
        initModel(model);
        model.addAttribute("modifyForm", new ModifyForm());
        return "curriculums/modifyForm";
    }

    @PostMapping("/modify-check")
    public String checkCurriculumExist(
            @ModelAttribute @Valid ModifyForm modifyForm, BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (service.isNotExist(modifyForm)) {
            result.reject("curriculum.notexist");
        }

        if (result.hasErrors()) {
            initModel(model);
            model.addAttribute("modifyForm", modifyForm);
            return "curriculums/modifyForm";
        }

        setRedirectAttributes(redirectAttributes, modifyForm);
        return "redirect:modify/detail";
    }

    @GetMapping("/modify/detail")
    public String modifyDetailForm(
            @ModelAttribute ModifyParam modifyParam, Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        model.addAttribute("modifyParam", modifyParam);
        log.info("modifyDetailForm:{}", modifyParam);
        return "curriculums/modifyDetailForm";
    }

    @GetMapping("/modify/detail/required-credit")
    public String modifyCreditForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("requiredCreditForm", curriculum.getRequiredCredit());
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/requiredCredit";
    }

    @PostMapping("/modify/detail/required-credit")
    public String modifyCredit(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid RequiredCreditForm requiredCreditForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);
            return "curriculums/detail/requiredCredit";
        }

        Curriculum curriculum = service.modifyRequiredCredit(modifyParam, requiredCreditForm);
        log.info("요구학점 수정 성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/sw-ai")
    public String modifySwAiForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("swAiForm", CurriculumConverter.to(curriculum.getSwAi()));
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/swAi";
    }

    @PostMapping("/modify/detail/sw-ai")
    public String modifySwAi(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid SwAiForm swAiForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (result.hasFieldErrors("approvedCodeSet[]")) {
                result.rejectValue("approvedCodeSet", "Code");
            }
            if (result.hasFieldErrors("areaAlternativeCodeSet[]")) {
                result.rejectValue("areaAlternativeCodeSet", "Code");
            }
            if (isAdditionalInfosError(result)) {
                result.rejectValue("additionalInfos", "Entry");
            }

            return "curriculums/detail/swAi";
        }

        Curriculum curriculum = service.modifySwAi(modifyParam, swAiForm);
        log.info("swAi 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/major-required")
    public String modifyMajorRequiredForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("majorRequiredForm",
                CurriculumConverter.to(curriculum.getMajorRequired()));
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/majorRequired";
    }

    @PostMapping("/modify/detail/major-required")
    public String modifyMajorRequired(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid MajorRequiredForm majorRequiredForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {

        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (result.hasFieldErrors("codeSet[]")) {
                result.rejectValue("codeSet", "Code");
            }

            if (isAdditionalInfosError(result)) {
                result.rejectValue("additionalInfos", "Entry");
            }

            return "curriculums/detail/majorRequired";
        }

        Curriculum curriculum = service.modifyMajorRequired(modifyParam, majorRequiredForm);
        log.info("전공필수 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/core")
    public String modifyCoreForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("coreForm", CurriculumConverter.to(curriculum.getCore()));
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/core";
    }

    @PostMapping("/modify/detail/core")
    public String modifyCore(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid CoreForm coreForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);
            checkCormForm(result);
            return "curriculums/detail/core";
        }

        Curriculum curriculum = service.modifyCore(modifyParam, coreForm);
        log.info("core 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/creativity")
    public String modifyCreativityForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("creativityForm", CurriculumConverter.to(curriculum.getCreativity()));
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/creativity";
    }

    @PostMapping("/modify/detail/creativity")
    public String modifyCreativity(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid CreativityForm creativityForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (isAdditionalInfosError(result)) {
                result.rejectValue("additionalInfos", "Entry");
            }

            if (result.hasFieldErrors("approvedCodeSet[]")) {
                result.rejectValue("approvedCodeSet", "Code");
            }
            return "curriculums/detail/creativity";
        }

        Curriculum curriculum = service.modifyCreativity(modifyParam, creativityForm);
        log.info("creativity 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/general-required")
    public String modifyGeneralRequiredForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("generalRequiredForm",
                CurriculumConverter.to(curriculum.getGeneralRequired())
        );
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/generalRequired";
    }

    @PostMapping("/modify/detail/general-required")
    public String modifyGeneralRequired(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid GeneralRequiredForm generalRequiredForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (result.hasFieldErrors("codeSet[]")) {
                result.rejectValue("codeSet", "Code");
            }

            if (isAdditionalInfosError(result)) {
                result.rejectValue("additionalInfos", "Entry");
            }

            return "curriculums/detail/generalRequired";
        }

        Curriculum curriculum = service.modifyGeneralRequired(modifyParam, generalRequiredForm);
        log.info("교양필수 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/major-select")
    public String modifyMajorSelectForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("majorSelectForm", CurriculumConverter.to(curriculum.getMajorSelect()));
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/majorSelect";
    }

    @PostMapping("/modify/detail/major-select")
    public String modifyMajorSelect(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid MajorSelectForm majorSelectForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (result.hasFieldErrors("codeSet[]")) {
                result.rejectValue("codeSet", "Code");
            }

            if (isAdditionalInfosError(result)) {
                result.rejectValue("additionalInfos", "Entry");
            }

            return "curriculums/detail/majorSelect";
        }

        Curriculum curriculum = service.modifyMajorSelect(modifyParam, majorSelectForm);
        log.info("전공선택 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    @GetMapping("/modify/detail/alternative-course")
    public String modifyAlternativeCourseForm(
            @ModelAttribute ModifyParam modifyParam,
            Model model
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비정상접근 입니다.");
            return "redirect:/admin";
        }

        Curriculum curriculum = service.getCurriculum(modifyParam);
        model.addAttribute("alternativeCourseForm",
                CurriculumConverter.to(curriculum.getAlternativeCourse())
        );
        model.addAttribute("modifyParam", modifyParam);

        return "curriculums/detail/alternativeCourse";
    }

    @PostMapping("/modify/detail/alternative-course")
    public String modifyAlternativeCourse(
            @ModelAttribute ModifyParam modifyParam,
            @ModelAttribute @Valid AlternativeCourseForm alternativeCourseForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (service.isNotExist(modifyParam)) {
            log.error("비 정상접근 입니다.");
            return "redirect:/admin";
        }

        if (result.hasErrors()) {
            log.error("bindingResult:{}", result);

            if (isAlternativeCourseFormError(result)) {
                result.reject("Code");
            }

            return "curriculums/detail/alternativeCourse";
        }

        Curriculum curriculum = service.modifyAlternativeCourse(modifyParam,
                alternativeCourseForm);
        log.info("대체과목 수정성공:{}", curriculum);

        setRedirectAttributes(redirectAttributes, modifyParam);
        return "redirect:/admin/curriculum/modify/detail";
    }

    private void initModel(Model model) {
        model.addAttribute("departmentNames", DepartmentName.values());
        model.addAttribute("majorTypes", MajorType.values());
        model.addAttribute("joinYears",
                List.of("2018", "2019", "2020", "2021", "2022", "2023", "2024"));
        model.addAttribute("lastEditors", LastEditor.values());
    }

    private void setRedirectAttributes(
            RedirectAttributes redirectAttributes,
            ModifyParam param
    ) {
        redirectAttributes.addAttribute("majorType", param.getMajorType());
        redirectAttributes.addAttribute("departmentName", param.getDepartmentName());
        redirectAttributes.addAttribute("joinYear", param.getJoinYear());
        redirectAttributes.addAttribute("lastEditor", param.getLastEditor());
    }

    private void setRedirectAttributes(
            RedirectAttributes redirectAttributes,
            ModifyForm form
    ) {
        redirectAttributes.addAttribute("majorType", form.getMajorType());
        redirectAttributes.addAttribute("departmentName", form.getDepartmentName());
        redirectAttributes.addAttribute("joinYear", form.getJoinYear());
        redirectAttributes.addAttribute("lastEditor", form.getLastEditor());
    }

    private boolean isAdditionalInfosError(BindingResult result) {
        boolean isError = false;
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError.getField().startsWith("additionalInfos[")) {
                isError = true;
            }
        }

        return isError;
    }

    private boolean isAlternativeCourseFormError(BindingResult result) {
        boolean isError = false;
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError.getField().startsWith("codes[")) {
                isError = true;
            }
        }

        return isError;
    }

    private void checkCormForm(BindingResult result) {
        if (isAdditionalInfosError(result)) {
            result.rejectValue("additionalInfos", "Entry");
        }

        if (result.hasFieldErrors("requiredAreaSet[]")) {
            result.rejectValue("requiredAreaSet", "ValidCategory");
        }

        if (result.hasFieldErrors("areaDeterminedCodeSetOne[]")) {
            result.rejectValue("areaDeterminedCodeSetOne", "Code");
        }
        if (result.hasFieldErrors("areaDeterminedCodeSetTwo[]")) {
            result.rejectValue("areaDeterminedCodeSetTwo", "Code");
        }
        if (result.hasFieldErrors("areaDeterminedCodeSetThree[]")) {
            result.rejectValue("areaDeterminedCodeSetThree", "Code");
        }
        if (result.hasFieldErrors("areaDeterminedCodeSetFour[]")) {
            result.rejectValue("areaDeterminedCodeSetFour", "Code");
        }
        if (result.hasFieldErrors("areaDeterminedCodeSetFive[]")) {
            result.rejectValue("areaDeterminedCodeSetFive", "Code");
        }
        if (result.hasFieldErrors("areaDeterminedCodeSetSix[]")) {
            result.rejectValue("areaDeterminedCodeSetSix", "Code");
        }

        if (result.hasFieldErrors("areaAlternativeCodeSetOne[]")) {
            result.rejectValue("areaAlternativeCodeSetOne", "Code");
        }
        if (result.hasFieldErrors("areaAlternativeCodeSetTwo[]")) {
            result.rejectValue("areaAlternativeCodeSetTwo", "Code");
        }
        if (result.hasFieldErrors("areaAlternativeCodeSetThree[]")) {
            result.rejectValue("areaAlternativeCodeSetThree", "Code");
        }
        if (result.hasFieldErrors("areaAlternativeCodeSetFour[]")) {
            result.rejectValue("areaAlternativeCodeSetFour", "Code");
        }
        if (result.hasFieldErrors("areaAlternativeCodeSetFive[]")) {
            result.rejectValue("areaAlternativeCodeSetFive", "Code");
        }
        if (result.hasFieldErrors("areaAlternativeCodeSetSix[]")) {
            result.rejectValue("areaAlternativeCodeSetSix", "Code");
        }
    }

}
