package com.example.library.service.Impl;

import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.repository.LoanRepository;
import com.example.library.service.interfaces.BookService;
import com.example.library.service.interfaces.MemberService;
import com.example.library.service.interfaces.NotificationService;
import com.example.library.service.interfaces.LoanService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    public LoanServiceImpl(
        LoanRepository loanRepository,
        BookService bookService,
        MemberService memberService,
        NotificationService notificationService
    ) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.memberService = memberService;
        this.notificationService = notificationService;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public Loan createLoan(Loan loan) {
        // Validation: Check if book exists
        Book book = bookService.getBookById(loan.getBookId());

        // Validation: Check if member exists
        Member member = memberService.getMemberById(loan.getMemberId());

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

    @Override
    public Loan returnBook(Long loanId) {
        Loan loan = getLoanById(loanId);

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        loan.setReturnDate(LocalDate.now());

        // Send notification to member
        Member member = memberService.getMemberById(loan.getMemberId());
        Book book = bookService.getBookById(loan.getBookId());
        notificationService.sendNotification(member, "Thank you for returning '" + book.getTitle() + "'.");

        return loanRepository.save(loan);
    }
}