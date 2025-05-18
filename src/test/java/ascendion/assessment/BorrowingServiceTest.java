package ascendion.assessment;

import ascendion.assessment.dto.request.ReturnBookRequestDto;
import ascendion.assessment.dto.response.ReturnBookResponseDto;
import ascendion.assessment.exception.BusinessException;
import ascendion.assessment.model.Book;
import ascendion.assessment.model.Borrower;
import ascendion.assessment.model.Borrowing;
import ascendion.assessment.repository.BorrowingRepository;
import ascendion.assessment.service.impl.BorrowingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    private Book book;
    private Borrower borrower;
    private Borrowing borrowing;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "1234", "Book Title", "Author");
        borrower = new Borrower(1L, "Alice", "alice@example.com");
        borrowing = new Borrowing(1L, book, borrower, LocalDate.now().minusDays(3), null, true);
    }

    @Test
    void returnBook_shouldMarkAsReturned_WhenValidRequest() {
        ReturnBookRequestDto request = new ReturnBookRequestDto(book.getId(), borrower.getId());

        when(borrowingRepository.findByBookIdAndActiveTrue(book.getId())).thenReturn(Optional.of(borrowing));

        ReturnBookResponseDto response = borrowingService.returnBook(request);

        assertNotNull(response);
        assertEquals(book.getId(), response.bookId());
        assertEquals(borrowing.getId(), response.borrowingId());
        assertNotNull(response.returnDate());

        assertFalse(borrowing.isActive());
        assertEquals(LocalDate.now(), borrowing.getReturnDate());

        verify(borrowingRepository).save(borrowing);
    }

    @Test
    void returnBook_shouldThrow_WhenNoActiveBorrowingFound() {
        ReturnBookRequestDto request = new ReturnBookRequestDto(book.getId(), borrower.getId());

        when(borrowingRepository.findByBookIdAndActiveTrue(book.getId())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("book.borrow.not.found"), any(), any(Locale.class))).thenReturn("Active borrowing not found");

        BusinessException ex = assertThrows(BusinessException.class, () -> borrowingService.returnBook(request));
        assertEquals("Active borrowing not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void returnBook_shouldThrow_WhenBorrowerDoesNotMatch() {
        Borrower wrongBorrower = new Borrower(2L, "Bob", "bob@example.com");
        ReturnBookRequestDto request = new ReturnBookRequestDto(book.getId(), wrongBorrower.getId());

        when(borrowingRepository.findByBookIdAndActiveTrue(book.getId())).thenReturn(Optional.of(borrowing));
        when(messageSource.getMessage(eq("book.borrow.not.by.user"), any(), any(Locale.class))).thenReturn("Book was not borrowed by this user");

        BusinessException ex = assertThrows(BusinessException.class, () -> borrowingService.returnBook(request));
        assertEquals("Book was not borrowed by this user", ex.getMessage());
    }
}
