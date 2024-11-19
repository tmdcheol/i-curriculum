package icurriculum.global.response.exception.handler;

import icurriculum.global.response.ApiResponse;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException ex) {
        log.error("Error Code: {}, Message: {}, Data: {}",
                ex.getErrorStatus().getCode(),
                ex.getErrorStatus().getMessage(),
                ex.getData() != null ? ex.getData() : "No additional data",
                ex
        );

        return ResponseEntity
                .status(ex.getErrorStatus().getHttpStatus())
                .body(ApiResponse.onFailure(
                        ex.getErrorStatus(),
                        ex.getData()
                ));
    }

    /*
     * Todo MethodArgumentNotValidException 이 다른 상황에서도 발생하면 수정 해야함.
     * validation, 학수번호 검증 에러 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        log.error("Validation error: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(ErrorStatus.CODE_IS_NOT_VALID.getHttpStatus())
                .body(ApiResponse.onFailure(
                        ErrorStatus.CODE_IS_NOT_VALID,
                        ErrorStatus.CODE_IS_NOT_VALID.getMessage()
                ));
    }
}