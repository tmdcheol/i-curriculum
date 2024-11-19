package icurriculum.admin.web.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyParam {

    @NotBlank
    private String majorType;

    @NotBlank
    private String departmentName;

    @NotNull
    @Min(value = 2018)
    @Max(value = 2024)
    private Integer joinYear;

    @NotBlank
    private String lastEditor;

}
