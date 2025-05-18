package ascendion.assessment.service.impl;

import ascendion.assessment.dto.BookSummaryDto;
import ascendion.assessment.dto.request.RegisterBookRequestDto;
import ascendion.assessment.dto.response.RegisterBookResponseDto;
import ascendion.assessment.exception.BusinessException;
import ascendion.assessment.model.Book;
import ascendion.assessment.repository.BookRepository;
import ascendion.assessment.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MessageSource messageSource;

    public BookServiceImpl(BookRepository bookRepository, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.messageSource = messageSource;
    }

    @Override
    public RegisterBookResponseDto registerNewBook(final RegisterBookRequestDto request) {
        log.info(" validation: enforce same ISBN has same title & author {}:", request);
        final List<Book> booksWithSameIsbn = bookRepository.findByIsbn(request.isbn());

        if (!booksWithSameIsbn.isEmpty()) {
            Book existing = booksWithSameIsbn.get(0);
            if (!existing.getTitle().equals(request.title()) ||
                    !existing.getAuthor().equals(request.author())) {
                throw new BusinessException(messageSource.getMessage("book.isbn.mismatch", null, Locale.getDefault()));
            }
        }

        final Book newBook = new Book();
        newBook.setIsbn(request.isbn());
        newBook.setTitle(request.title());
        newBook.setAuthor(request.author());
        log.info("saving book {}:", newBook);
        final Book savedBook = bookRepository.save(newBook);
        return new RegisterBookResponseDto(savedBook.getId(), savedBook.getIsbn(), savedBook.getTitle(), savedBook.getAuthor());
    }

    @Override
    public Page<BookSummaryDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(book -> new BookSummaryDto(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor()));
    }
}
