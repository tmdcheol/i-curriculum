package icurriculum.admin.config;

import icurriculum.global.response.exception.GeneralException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class AdminGlobalControllerAdvice {

    @ExceptionHandler(GeneralException.class)
    public ModelAndView handleGeneralException(GeneralException ex) {
        ModelAndView modelAndView = new ModelAndView("error/errorPage");

        modelAndView.addObject("errorStatus", ex.getErrorStatus());
        modelAndView.addObject("errorStatusMessage", ex.getErrorStatus().getMessage());

        modelAndView.addObject("errorMessage", ex.getMessage());

        return modelAndView;
    }

}
