package com.example.library.service;
import com.example.library.model.Book;
import com.example.library.model.Member;
import com.example.library.model.Loan;
import com.example.library.repository.BookRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.repository.LoanRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    
    public LibraryService(BookRepository bookRepository, 
                         MemberRepository memberRepository,
                         LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }
    
    // ---- Book Management ----
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    }
    
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationYear(bookDetails.getPublicationYear());
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
    
    // ---- Member Management ----
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found"));
    }
    
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
    
    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        return memberRepository.save(member);
    }
    
    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }
    
    // ---- Loan Management ----
    
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
    }
    
    public Loan createLoan(Loan loan) {
        // Validation: Check if book exists
        Book book = getBookById(loan.getBookId());
        
        // Validation: Check if member exists
        Member member = getMemberById(loan.getMemberId());
        
        // Validation: Check if book is already loaned
        Optional<Loan> existingLoan = loanRepository.findByBookIdAndReturnDateIsNull(loan.getBookId());
        if (existingLoan.isPresent()) {
            throw new RuntimeException("Book is already loaned");
        }
        
        // Set loan dates
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks loan period
        
        // Save and return the loan
        return loanRepository.save(loan);
    }
    
    public Loan returnBook(Long loanId) {
        Loan loan = getLoanById(loanId);
        
        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }
        
        loan.setReturnDate(LocalDate.now());
        
        // Send notification to member
        Member member = getMemberById(loan.getMemberId());
        Book book = getBookById(loan.getBookId());
        sendNotification(member, "Thank you for returning '" + book.getTitle() + "'.");
        
        return loanRepository.save(loan);
    }
    
    // ---- Notification Logic ----
    
    public void sendNotification(Member member, String message) {
        // This is a simulation of sending notification
        System.out.println("Notification to " + member.getName() + " (" + member.getEmail() + "): " + message);
    }
    
    // This method mixes book management with notification, violating SRP
    public void notifyDueBooks() {
        LocalDate today = LocalDate.now();
        
        List<Loan> loans = loanRepository.findAll();
        
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null && loan.getDueDate().isBefore(today)) {
                Member member = getMemberById(loan.getMemberId());
                Book book = getBookById(loan.getBookId());
                
                sendNotification(
                    member, 
                    "The book '" + book.getTitle() + "' is overdue. Please return it as soon as possible."
                );
            }
        }
    }
}