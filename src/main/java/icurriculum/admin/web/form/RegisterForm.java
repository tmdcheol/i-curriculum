package icurriculum.admin.web.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "올바르지 않은 전공상태 값 입니다.")
    private String majorType;

    @NotBlank(message = "올바르지 않은 학과이름 값 입니다.")
    private String departmentName;

    @NotNull(message = "올바르지 않은 입학년도 값 입니다.")
    @Min(value = 2018, message = "18학번 이후부터 지원합니다.")
    @Max(value = 2024, message = "24학번까지만 지원합니다.")
    private Integer joinYear;

    @NotBlank(message = "올바르지 않은 등록자 입니다.")
    private String lastEditor;

}
