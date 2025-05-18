package ascendion.assessment.repository;

import ascendion.assessment.model.Book;
import ascendion.assessment.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    boolean existsByBookAndActiveTrue(final Book book);

    Optional<Borrowing> findByBookIdAndActiveTrue(final Long bookId);
}