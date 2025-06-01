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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookWebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookWebController.class);

    @Autowired
    private FictionBookRepository fictionBookRepository;

    @Autowired
    private NonFictionBookRepository nonFictionBookRepository;

    // Существующие методы (оставляем без изменений)
    @GetMapping("/")
    public String getAllBooks(Model model) {
        LOGGER.debug("Fetching all books for main page");
        List<BookItem> books = new ArrayList<>();
        books.addAll(fictionBookRepository.findAll());
        books.addAll(nonFictionBookRepository.findAll());
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/fiction/{id}")
    public String getFictionBook(@PathVariable Long id, Model model) {
        LOGGER.debug("Fetching fiction book details with id: {}", id);
        FictionBook book = fictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fiction book not found with id: " + id));
        model.addAttribute("book", book);
        model.addAttribute("bookType", "Fiction");
        return "book-details";
    }

    @GetMapping("/nonfiction/{id}")
    public String getNonFictionBook(@PathVariable Long id, Model model) {
        LOGGER.debug("Fetching non-fiction book details for id: {}", id);
        NonFictionBook book = nonFictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-fiction book not found with id: " + id));
        model.addAttribute("book", book);
        model.addAttribute("bookType", "Non-Fiction");
        return "book-details";
    }

    @GetMapping("/add-fiction")
    public String showAddFictionForm(Model model) {
        LOGGER.debug("Showing add fiction book form");
        model.addAttribute("fictionBook", new FictionBook());
        return "add-fiction";
    }

    @PostMapping("/add-fiction")
    public String addFictionBook(@Valid @ModelAttribute("fictionBook") FictionBook fictionBook, BindingResult result) {
        LOGGER.debug("Attempting to add fiction book: {}", fictionBook.getTitle());
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for fiction book: {}", result.getAllErrors());
            return "add-fiction";
        }
        fictionBookRepository.save(fictionBook);
        LOGGER.info("Added fiction book: {}", fictionBook.getTitle());
        return "redirect:/";
    }

    @GetMapping("/add-nonfiction")
    public String showAddNonFictionForm(Model model) {
        LOGGER.debug("Showing add non-fiction book form");
        model.addAttribute("nonFictionBook", new NonFictionBook());
        return "add-nonfiction";
    }

    @PostMapping("/add-nonfiction")
    public String addNonFictionBook(@Valid @ModelAttribute("nonFictionBook") NonFictionBook nonFictionBook, BindingResult result) {
        LOGGER.debug("Attempting to add non-fiction book: {}", nonFictionBook.getTitle());
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for non-fiction book: {}", result.getAllErrors());
            return "add-nonfiction";
        }
        nonFictionBookRepository.save(nonFictionBook);
        LOGGER.info("Added non-fiction book: {}", nonFictionBook.getTitle());
        return "redirect:/";
    }

    @GetMapping("/edit-fiction/{id}")
    public String showEditFictionForm(@PathVariable Long id, Model model) {
        LOGGER.debug("Showing edit fiction book form for id: {}", id);
        FictionBook book = fictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fiction book not found with id: " + id));
        model.addAttribute("fictionBook", book);
        return "edit-fiction";
    }

    @PostMapping("/edit-fiction/{id}")
    public String editFictionBook(@PathVariable Long id, @Valid @ModelAttribute("fictionBook") FictionBook fictionBook, BindingResult result) {
        LOGGER.debug("Attempting to update fiction book with id: {}", id);
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for fiction book id: {}", id);
            return "edit-fiction";
        }
        FictionBook existing = fictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fiction book not found with id: " + id));
        existing.setTitle(fictionBook.getTitle());
        existing.setAuthor(fictionBook.getAuthor());
        existing.setGenre(fictionBook.getGenre());
        fictionBookRepository.save(existing);
        LOGGER.info("Updated fiction book with id: {}", id);
        return "redirect:/";
    }

    @GetMapping("/edit-nonfiction/{id}")
    public String showEditNonFictionForm(@PathVariable Long id, Model model) {
        LOGGER.debug("Showing edit non-fiction book form for id: {}", id);
        NonFictionBook book = nonFictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-fiction book not found with id: " + id));
        model.addAttribute("nonFictionBook", book);
        return "edit-nonfiction";
    }

    @PostMapping("/edit-nonfiction/{id}")
    public String editNonFictionBook(@PathVariable Long id, @Valid @ModelAttribute("nonFictionBook") NonFictionBook nonFictionBook, BindingResult result) {
        LOGGER.debug("Attempting to update non-fiction book with id: {}", id);
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for non-fiction book id: {}", id);
            return "edit-nonfiction";
        }
        NonFictionBook existing = nonFictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-fiction book not found with id: " + id));
        existing.setTitle(nonFictionBook.getTitle());
        existing.setAuthor(nonFictionBook.getAuthor());
        existing.setSubject(nonFictionBook.getSubject());
        nonFictionBookRepository.save(existing);
        LOGGER.info("Updated non-fiction book with id: {}", id);
        return "redirect:/";
    }

    @GetMapping("/delete-fiction/{id}")
    public String deleteFictionBook(@PathVariable Long id) {
        LOGGER.debug("Deleting fiction book with id: {}", id);
        fictionBookRepository.deleteById(id);
        LOGGER.info("Deleted fiction book with id: {}", id);
        return "redirect:/";
    }

    @GetMapping("/delete-nonfiction/{id}")
    public String deleteNonFictionBook(@PathVariable Long id) {
        LOGGER.debug("Deleting non-fiction book with id: {}", id);
        nonFictionBookRepository.deleteById(id);
        LOGGER.info("Deleted non-fiction book with id: {}", id);
        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException ex, Model model) {
        LOGGER.error("Error: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    // Новые методы PATCH
    @RequestMapping(value = "/patch-fiction/{id}", method = RequestMethod.POST, params = "_method=PATCH")
    public String patchFictionBook(@PathVariable Long id, @ModelAttribute("fictionBook") FictionBook fictionBook, BindingResult result) {
        LOGGER.debug("Patching fiction book with id: {}", id);
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for fiction book id: {}", id);
            return "edit-fiction";
        }
        FictionBook existing = fictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fiction book not found with id: " + id));
        if (fictionBook.getTitle() != null && !fictionBook.getTitle().isBlank()) {
            existing.setTitle(fictionBook.getTitle());
        }
        if (fictionBook.getAuthor() != null && !fictionBook.getAuthor().isBlank()) {
            existing.setAuthor(fictionBook.getAuthor());
        }
        if (fictionBook.getGenre() != null && !fictionBook.getGenre().isBlank()) {
            existing.setGenre(fictionBook.getGenre());
        }
        fictionBookRepository.save(existing);
        LOGGER.info("Patched fiction book with id: {}", id);
        return "redirect:/";
    }

    @RequestMapping(value = "/patch-nonfiction/{id}", method = RequestMethod.POST, params = "_method=PATCH")
    public String patchNonFictionBook(@PathVariable Long id, @ModelAttribute("nonFictionBook") NonFictionBook nonFictionBook, BindingResult result) {
        LOGGER.debug("Patching non-fiction book with id: {}", id);
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors for non-fiction book id: {}", id);
            return "edit-nonfiction";
        }
        NonFictionBook existing = nonFictionBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-fiction book not found with id: " + id));
        if (nonFictionBook.getTitle() != null && !nonFictionBook.getTitle().isBlank()) {
            existing.setTitle(nonFictionBook.getTitle());
        }
        if (nonFictionBook.getAuthor() != null && !nonFictionBook.getAuthor().isBlank()) {
            existing.setAuthor(nonFictionBook.getAuthor());
        }
        if (nonFictionBook.getSubject() != null && !nonFictionBook.getSubject().isBlank()) {
            existing.setSubject(nonFictionBook.getSubject());
        }
        nonFictionBookRepository.save(existing);
        LOGGER.info("Patched non-fiction book with id: {}", id);
        return "redirect:/";
    }
}
