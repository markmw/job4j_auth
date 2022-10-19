package ru.job4j.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ValidationControllerAdvise {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        List<Map<String, String>> list = new ArrayList<>();
        for (FieldError f : e.getFieldErrors()) {
            Map<String, String> field = Map.of(f.getField(), Objects.requireNonNull(f.getDefaultMessage()));
            list.add(field);
        }
        return ResponseEntity.badRequest().body(list);
    }
}
