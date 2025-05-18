package ascendion.assessment.service.impl;

import ascendion.assessment.dto.request.BorrowBookRequestDto;
import ascendion.assessment.dto.request.ReturnBookRequestDto;
import ascendion.assessment.dto.response.BorrowBookResponseDto;
import ascendion.assessment.dto.response.ReturnBookResponseDto;
import ascendion.assessment.exception.BusinessException;
import ascendion.assessment.model.Book;
import ascendion.assessment.model.Borrower;
import ascendion.assessment.model.Borrowing;
import ascendion.assessment.repository.BookRepository;
import ascendion.assessment.repository.BorrowerRepository;
import ascendion.assessment.repository.BorrowingRepository;
import ascendion.assessment.service.BorrowingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;

@Slf4j
@Service
public class BorrowingServiceImpl implements BorrowingService {
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowingRepository borrowingRepository;
    private final MessageSource messageSource;

    public BorrowingServiceImpl(BookRepository bookRepository, BorrowerRepository borrowerRepository, BorrowingRepository borrowingRepository, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowingRepository = borrowingRepository;
        this.messageSource = messageSource;
    }


    @Transactional
    @Override
    public BorrowBookResponseDto borrowBook(BorrowBookRequestDto request) {
        log.info("BorrowBookRequestDto {}:", request);

        final Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("book.not.found", null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        if (borrowingRepository.existsByBookAndActiveTrue(book)) {
            throw new BusinessException(messageSource.getMessage("book.already.borrowed", null, Locale.getDefault()));
        }

        final Borrower borrower = borrowerRepository.findById(request.borrowerId())
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("borrower.not.found", null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        final Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setBorrower(borrower);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setReturnDate(null);
        borrowing.setActive(true);

        final Borrowing savedRecord = borrowingRepository.save(borrowing);

        return new BorrowBookResponseDto(savedRecord.getId(), savedRecord.getBook().getId(), savedRecord.getBook().getTitle(), savedRecord.getBook().getIsbn(), savedRecord.getBook().getAuthor(), savedRecord.getBorrower().getId(), savedRecord.getBorrower().getName(), savedRecord.getBorrowDate());
    }

    @Override
    @Transactional
    public ReturnBookResponseDto returnBook(ReturnBookRequestDto request) {
        log.info("ReturnBookRequestDto {}:", request);

        final Borrowing borrowing = borrowingRepository.findByBookIdAndActiveTrue(request.bookId())
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("book.borrow.not.found", null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        if (!borrowing.getBorrower().getId().equals(request.borrowerId())) {
            throw new BusinessException(messageSource.getMessage("book.borrow.not.by.user", null, Locale.getDefault()));
        }

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setActive(false);
        borrowingRepository.save(borrowing);

        return new ReturnBookResponseDto(borrowing.getId(), borrowing.getBook().getId(), borrowing.getReturnDate());
    }
}
