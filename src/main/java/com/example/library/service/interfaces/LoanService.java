package com.example.library.service.interfaces;

import com.example.library.model.Loan;
import java.util.List;

public interface LoanService {
    List<Loan> getAllLoans();
Loan getLoanById(Long id);
Loan createLoan(Loan loan);
Loan returnBook(Long loanId);
}
