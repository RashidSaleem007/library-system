package ascendion.assessment;

import ascendion.assessment.dto.BookSummaryDto;
import ascendion.assessment.dto.request.RegisterBookRequestDto;
import ascendion.assessment.dto.response.RegisterBookResponseDto;
import ascendion.assessment.model.Book;
import ascendion.assessment.repository.BookRepository;
import ascendion.assessment.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void registerNewBook_shouldSaveAndReturnResponse() {
        RegisterBookRequestDto request = new RegisterBookRequestDto("1234", "Test Book", "Test Author");
        Book savedBook = new Book(1L, "1234", "Test Book", "Test Author");

        when(bookRepository.findByIsbn("1234")).thenReturn(List.of());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        RegisterBookResponseDto response = bookService.registerNewBook(request);

        assertEquals("1234", response.isbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void getAllBooks_shouldReturnPagedResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(new Book(1L, "1234", "Title", "Author")));

        when(bookRepository.findAll(pageable)).thenReturn(mockPage);

        Page<BookSummaryDto> result = bookService.getAllBooks(pageable);
        assertEquals(1, result.getTotalElements());
    }
}
