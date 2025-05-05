package com.example.library.service.Impl;


import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.interfaces.BookService;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

public BookServiceImpl(BookRepository bookRepository) {
this.bookRepository = bookRepository;
}
@Override
public List<Book> getAllBooks() {
return bookRepository.findAll();
}
@Override
public Book getBookById(Long id) {
return bookRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Book not found"));
}
@Override
public Book createBook(Book book) {
return bookRepository.save(book);
}
@Override
public Book updateBook(Long id, Book bookDetails) {
Book book = getBookById(id);
book.setTitle(bookDetails.getTitle());
book.setAuthor(bookDetails.getAuthor());
book.setIsbn(bookDetails.getIsbn());
book.setPublicationYear(bookDetails.getPublicationYear());
return bookRepository.save(book);
}
@Override
public void deleteBook(Long id) {
Book book = getBookById(id);
bookRepository.delete(book);
}
}
