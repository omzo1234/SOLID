package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.library.model.*;
import com.example.library.repository.*;
import java.time.LocalDate;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner dataLoader(BookRepository bookRepository, 
                                       MemberRepository memberRepository,
                                       LoanRepository loanRepository) {
        return args -> {
            // Ajout de quelques livres de démonstration
            Book book1 = new Book("Introduction aux Microservices", "Sam Newman", "978-1491950357", 2019);
            Book book2 = new Book("Clean Code", "Robert C. Martin", "978-0132350884", 2008);
            Book book3 = new Book("Design Patterns", "Erich Gamma", "978-0201633610", 1994);
            
            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            
            // Ajout de quelques membres
            Member member1 = new Member("Jean Dupont", "jean.dupont@email.com", "0123456789");
            Member member2 = new Member("Marie Martin", "marie.martin@email.com", "0987654321");
            
            memberRepository.save(member1);
            memberRepository.save(member2);
            
            // Création d'un prêt
            Loan loan = new Loan();
            loan.setBookId(book1.getId());
            loan.setMemberId(member1.getId());
            loan.setLoanDate(LocalDate.now().minusDays(7));
            loan.setDueDate(LocalDate.now().plusDays(7));
            
            loanRepository.save(loan);
        };
    }
}