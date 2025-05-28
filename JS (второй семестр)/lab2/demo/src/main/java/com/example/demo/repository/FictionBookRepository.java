package com.example.demo.repository;

import com.example.demo.model.FictionBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FictionBookRepository extends JpaRepository<FictionBook, Long> {
}