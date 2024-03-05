package com.fsociety.authapi.config;

import com.fsociety.authapi.utils.*;
import com.fsociety.authapi.utils.Error;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExecutionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseBody<Error>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        if (e.getRootCause() == null) {
            return new ResponseEntityBuilder<Error>()
                    .withData(new Error(ErrorId.DATA_INTEGRITY_VIOLATION.getId(), null, ""))
                    .withStatus(HttpStatus.CONFLICT).build();
        }
        String errorMessage = "Data integrity violation exception: " + e.getRootCause().getMessage();
        String field = extractFieldFromMessage(errorMessage);
        return new ResponseEntityBuilder<Error>()
                .withData(new Error(ErrorId.DATA_INTEGRITY_VIOLATION.getId(), field, errorMessage))
                .withStatus(HttpStatus.CONFLICT).build();
    }

    private String extractFieldFromMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseBody<List<Error>>> handleConstraintViolationException(ConstraintViolationException e) {
        List<Error> errors = collectConstraintVaErrors(e);
        return new ResponseEntityBuilder<List<Error>>()
                .withData(errors).withStatus(HttpStatus.BAD_REQUEST)
                .withMessage("Constraint violation exception").build();
    }

    private List<Error> collectConstraintVaErrors(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(violation -> new Error(
                        "Constraint_violation",
                        violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .toList();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseBody<Error>> handleNotFoundException(NotFoundException e) {
        return new ResponseEntityBuilder<Error>()
                .withData(new Error(ErrorId.NOT_FOUND.getId(), e.getResourceName(), e.getMessage()))
                .withStatus(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Error>> handleException(Exception e) {
        return new ResponseEntityBuilder<Error>()
                .withData(new Error(ErrorId.INTERNAL_SERVER_ERROR.getId(), null, e.getMessage()))
                .withMessage("Internal server error")
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
