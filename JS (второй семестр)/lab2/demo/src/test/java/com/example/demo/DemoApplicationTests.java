package com.example.demo;

import com.example.demo.model.FictionBook;
import com.example.demo.model.NonFictionBook;
import com.example.demo.repository.FictionBookRepository;
import com.example.demo.repository.NonFictionBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private FictionBookRepository fictionBookRepository;
	@Autowired
	private NonFictionBookRepository nonFictionBookRepository;

	@Test
	void testSaveFictionBook() {
		FictionBook book = new FictionBook("Test Book", "Test Author", "Test Genre");
		FictionBook savedBook = fictionBookRepository.save(book);
		assertNotNull(savedBook.getId());
	}

	@Test
	void testSaveNonFictionBook() {
		NonFictionBook book = new NonFictionBook("Test Non-Fiction", "Test Author", "Test Subject");
		NonFictionBook savedBook = nonFictionBookRepository.save(book);
		assertNotNull(savedBook.getId());
	}

	@Test
	void testDeleteFictionBook() {
		FictionBook book = new FictionBook("Test Book", "Test Author", "Test Genre");
		FictionBook savedBook = fictionBookRepository.save(book);
		fictionBookRepository.deleteById(savedBook.getId());
		assertTrue(fictionBookRepository.findById(savedBook.getId()).isEmpty());
	}
}