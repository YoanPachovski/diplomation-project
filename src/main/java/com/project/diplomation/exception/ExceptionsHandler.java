package com.project.diplomation.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    protected String handleException(Exception exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "/errors/errors";
    }

//    @ExceptionHandler({MedicineNotFoundException.class, RecipeNotFoundException.class})
//    public String handleException(MedicineNotFoundException exception, Model model) {
//        model.addAttribute("message", exception.getMessage());
//        return "/errors/not-found-errors";
//    }
}

