package com.example.blog_viewer_api.Repository;


import com.example.blog_viewer_api.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
