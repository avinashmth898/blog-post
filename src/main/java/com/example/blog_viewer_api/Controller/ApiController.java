package com.example.blog_viewer_api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/")
    public String home() {
        return "Welcome to Blog Viewer API ";
    }

    @GetMapping("/home")
    public String blogs() {
        return "Here will be your list of blogs!";
    }
}
