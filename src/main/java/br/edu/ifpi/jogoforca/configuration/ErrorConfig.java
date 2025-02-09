package br.edu.ifpi.jogoforca.configuration;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorConfig {
    
    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";  // Você precisará criar um template error.html
    }
} 