package icurriculum.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class ResultController {

    @GetMapping("/result/success")
    public String success(Model model) {
        // 성공 메시지를 모델에 추가
        model.addAttribute("message", "커리큘럼 등록에 성공했습니다.");
        return "result/success";  // success.html 페이지로 리턴
    }

    @GetMapping("/result/failure")
    public String failure(Model model) {
        // 중복 오류를 체크해서 메시지를 설정
        Boolean isDuplicated = (Boolean) model.asMap().get("isDuplicated");

        if (isDuplicated != null && isDuplicated) {
            model.addAttribute("message", "커리큘럼 등록 실패: 중복된 데이터입니다.");
        } else {
            model.addAttribute("message", "커리큘럼 등록에 실패했습니다.");
        }

        return "result/failure";  // failure.html 페이지로 리턴
    }

}
