package com.example.library.controller;

import com.example.library.model.Loan;
import com.example.library.service.LibraryService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LibraryService libraryService;
    
   
    public LoanController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    
    @GetMapping
    public List<Loan> getAllLoans() {
        return libraryService.getAllLoans();
    }
    
    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return libraryService.getLoanById(id);
    }
    
    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return libraryService.createLoan(loan);
    }
    
    @PutMapping("/{id}/return")
    public Loan returnBook(@PathVariable Long id) {
        return libraryService.returnBook(id);
    }
}