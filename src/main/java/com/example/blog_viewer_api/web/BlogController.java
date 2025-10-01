package com.example.blog_viewer_api.web;


import com.example.blog_viewer_api.Model.Blog;
import com.example.blog_viewer_api.Repository.BlogRepository;
import com.example.blog_viewer_api.dto.BlogSummeryDTO;
import jakarta.persistence.Id;
//import org.hibernate.mapping.Map;
//import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final BlogRepository repo;

    public BlogController(BlogRepository repo) {
        this.repo = repo;
    }

    //HomePage - list of all blogging
    @GetMapping
    public List<BlogSummeryDTO> list() {
        return repo.findAll().stream().map(BlogSummeryDTO::from).toList();
    }
    //BlogPage - find it with id
    @GetMapping("/{id}")
    public Blog getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog not found"));
    }
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//
//        if ("admin".equals(username) && "password".equals(password)) {
//            return "Login successful!";
//        }
//        return "Invalid credentials!";
//    }


    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        return repo.save(blog);
    }

    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody Blog updated) {
        return repo.findById(id)
                .map(b -> {
                    b.setTitle(updated.getTitle());
                    b.setContent(updated.getContent());
                    return repo.save(b);
                })
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Blog not found");
        }
        repo.deleteById(id);
    }

}
