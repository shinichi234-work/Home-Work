package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class NonFictionBook implements BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Subject is required")
    private String subject;

    public NonFictionBook() {}
    public NonFictionBook(String title, String author, String subject) {
        this.title = title;
        this.author = author;
        this.subject = subject;
    }

    @Override
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @Override
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}