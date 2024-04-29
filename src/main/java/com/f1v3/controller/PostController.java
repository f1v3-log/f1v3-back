package com.f1v3.controller;

import com.f1v3.dto.request.PostCreateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {


    /**
     * Controller 테스트용 메서드.
     *
     * @return status : 200, body : "Hello f1v3"
     */
    @GetMapping("/f1v3")
    public String getF1v3() {
        return "Hello f1v3";
    }


    /**
     * 게시글 생성 메서드
     *
     * @return status : 200, body : "Hello World"
     */
    @PostMapping("/posts")
    public Map<String, String> createPost(@RequestBody @Valid PostCreateRequest post,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);

            return error;
        }

        return Map.of();
    }
}
