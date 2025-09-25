package com.example.blog_viewer_api.dto;

import com.example.blog_viewer_api.Model.Blog;

import java.time.LocalDateTime;

public class BlogSummeryDTO {
    private long id;
    private String title;
    private String excerpt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BlogSummeryDTO ( long id, String title, String excerpt, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id=id;
        this.title=title;
        this.excerpt=excerpt;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;

    }

    public static BlogSummeryDTO from (Blog b){
        return new BlogSummeryDTO(
                b.getId(),
                b.getTitle(),
                BlogSummeryDTO.makeExcerpt(b.getContent()),
                b.getCreatedAt(),
                b.getUpdatedAt()

        );
    }

    private static String makeExcerpt(String content){
        if (content == null) return "";
        String plain = content.trim().replaceAll("\\s+" ," ");
        return plain.length()<=160 ? plain : plain.substring(0,157) + "...";
    }

    //getters


    public long getId() { return id; }
    public  String getTitle(){ return title; }
    public String getExcerpt(){ return excerpt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() {return updatedAt; }

}

