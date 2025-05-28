package com.example.demo.repository;

import com.example.demo.model.NonFictionBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonFictionBookRepository extends JpaRepository<NonFictionBook, Long> {
}