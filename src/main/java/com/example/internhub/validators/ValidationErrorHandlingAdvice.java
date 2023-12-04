package com.example.internhub.validators;

import com.example.internhub.responses.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class ValidationErrorHandlingAdvice {

//    /**
//     * Handler for {@link MethodArgumentNotValidException}s. These are triggered by
//     * Spring {@link Validator} implementations being invoked by the Spring MVC
//     * controllers and returning validation errors.
//     *
//     *
//     * @param ex The {@link MethodArgumentNotValidException} to be handled.
//     *
//     * @return {@link Map} keyed by field to which the error is bound and with the
//     *         value of the field as a value.
//     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ResponseObject handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse res) {
        Map<String, List<String>> errorMap = new TreeMap<>();
        System.out.println(errorMap.size());

        BindingResult result = ex.getBindingResult();
        for (FieldError error : result.getFieldErrors()) {

            if (!errorMap.containsKey(error.getField())) {
                errorMap.put(error.getField(), new ArrayList<String>());
            }

            errorMap.get(error.getField()).add(error.getDefaultMessage());
        }
        res.setStatus(400);
        return new ResponseObject(400, errorMap.toString(), null);
    }


    private static class ValidationErrors {
        private Map<String, List<String>> errors;

        public ValidationErrors(Map<String, List<String>> errors) {
            this.errors = errors;
        }

        @SuppressWarnings("unused")
        public Map<String, List<String>> getErrors() {
            return errors;
        }
    }
}