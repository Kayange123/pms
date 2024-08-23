package dev.kayange.projectmanagementsystem.exception;

import com.razorpay.RazorpayException;
import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.enumaration.ErrorCode;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> lockedExceptionHandler(LockedException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .code(ErrorCode.ACCOUNT_LOCKED.getCode())
                                .description(ErrorCode.ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(RazorpayException.class)
    public ResponseEntity<ExceptionResponse> razorPayExceptionHandler(RazorpayException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .description("Failed to Initiate Payments")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledExceptionHandler(DisabledException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .code(ErrorCode.ACCOUNT_DISABLED.getCode())
                                .error(exp.getMessage())
                                .description(ErrorCode.ACCOUNT_DISABLED.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .code(ErrorCode.BAD_CREDENTIALS.getCode())
                                .description(ErrorCode.BAD_CREDENTIALS.getDescription())
                                .error(ErrorCode.BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> messagingExceptionHandler(MessagingException exp){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        for (var error : exp.getBindingResult().getAllErrors()){
            errors.add(error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()

                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> customApiException(ApiException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .status(HttpStatus.BAD_REQUEST.toString())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> catchAllExceptions(Exception exception) {
        var response = ApiResponse.<ApiResponse<?>>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
