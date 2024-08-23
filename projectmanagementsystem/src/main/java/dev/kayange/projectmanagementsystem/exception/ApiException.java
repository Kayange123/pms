package dev.kayange.projectmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException{
    private String message;

}
