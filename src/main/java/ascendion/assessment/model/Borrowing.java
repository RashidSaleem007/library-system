package ascendion.assessment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "c_book_id", unique = true, nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "c_borrower_id", nullable = false)
    private Borrower borrower;

    @JoinColumn(name = "c_borrow_date")
    private LocalDate borrowDate;

    @JoinColumn(name = "c_return_date")
    private LocalDate returnDate;

    @Column(name = "c_active", nullable = false)
    private boolean active = true;  // true = currently borrowed, false = returned
}
