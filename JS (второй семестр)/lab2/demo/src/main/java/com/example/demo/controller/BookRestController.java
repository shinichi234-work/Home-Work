package com.example.demo.controller;

import com.example.demo.model.BookItem;
import com.example.demo.model.FictionBook;
import com.example.demo.model.NonFictionBook;
import com.example.demo.repository.FictionBookRepository;
import com.example.demo.repository.NonFictionBookRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    @Autowired
    private FictionBookRepository fictionBookRepository;
    @Autowired
    private NonFictionBookRepository nonFictionBookRepository;

    @GetMapping
    public List<BookItem> getAllBooks() {
        logger.debug("Fetching all books");
        List<BookItem> books = new ArrayList<>();
        books.addAll(fictionBookRepository.findAll());
        books.addAll(nonFictionBookRepository.findAll());
        return books;
    }

    @GetMapping("/fiction/{id}")
    public ResponseEntity<FictionBook> getFictionBook(@PathVariable Long id) {
        logger.debug("Fetching fiction book with id: {}", id);
        return fictionBookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Fiction book with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/nonfiction/{id}")
    public ResponseEntity<NonFictionBook> getNonFictionBook(@PathVariable Long id) {
        logger.debug("Fetching non-fiction book with id: {}", id);
        return nonFictionBookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Non-fiction book with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/fiction")
    public FictionBook addFictionBook(@Valid @RequestBody FictionBook book) {
        logger.info("Adding fiction book: {}", book.getTitle());
        return fictionBookRepository.save(book);
    }

    @PostMapping("/nonfiction")
    public NonFictionBook addNonFictionBook(@Valid @RequestBody NonFictionBook book) {
        logger.info("Adding non-fiction book: {}", book.getTitle());
        return nonFictionBookRepository.save(book);
    }

    @PutMapping("/fiction/{id}")
    public ResponseEntity<FictionBook> updateFictionBook(@PathVariable Long id, @Valid @RequestBody FictionBook book) {
        logger.debug("Updating fiction book with id: {}", id);
        return fictionBookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    existing.setGenre(book.getGenre());
                    logger.info("Updated fiction book: {}", book.getTitle());
                    return ResponseEntity.ok(fictionBookRepository.save(existing));
                })
                .orElseGet(() -> {
                    logger.warn("Fiction book with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/nonfiction/{id}")
    public ResponseEntity<NonFictionBook> updateNonFictionBook(@PathVariable Long id, @Valid @RequestBody NonFictionBook book) {
        logger.debug("Updating non-fiction book with id: {}", id);
        return nonFictionBookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    existing.setSubject(book.getSubject());
                    logger.info("Updated non-fiction book: {}", book.getTitle());
                    return ResponseEntity.ok(nonFictionBookRepository.save(existing));
                })
                .orElseGet(() -> {
                    logger.warn("Non-fiction book with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/fiction/{id}")
    public ResponseEntity<Void> deleteFictionBook(@PathVariable Long id) {
        logger.debug("Deleting fiction book with id: {}", id);
        if (fictionBookRepository.existsById(id)) {
            fictionBookRepository.deleteById(id);
            logger.info("Deleted fiction book with id: {}", id);
            return ResponseEntity.ok().build();
        }
        logger.warn("Fiction book with id {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/nonfiction/{id}")
    public ResponseEntity<Void> deleteNonFictionBook(@PathVariable Long id) {
        logger.debug("Deleting non-fiction book with id: {}", id);
        if (nonFictionBookRepository.existsById(id)) {
            nonFictionBookRepository.deleteById(id);
            logger.info("Deleted non-fiction book with id: {}", id);
            return ResponseEntity.ok().build();
        }
        logger.warn("Non-fiction book with id {} not found", id);
        return ResponseEntity.notFound().build();
    }
}