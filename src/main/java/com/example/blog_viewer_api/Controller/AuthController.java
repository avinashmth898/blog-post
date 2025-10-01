package com.example.blog_viewer_api.Controller;

//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // for login route
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // If we reach here → Basic Auth succeeded
        return ResponseEntity.ok("Login successful!");
    }
}