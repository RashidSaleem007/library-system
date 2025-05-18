package ascendion.assessment.controller;

import ascendion.assessment.dto.BookSummaryDto;
import ascendion.assessment.dto.request.BorrowBookRequestDto;
import ascendion.assessment.dto.request.RegisterBookRequestDto;
import ascendion.assessment.dto.request.ReturnBookRequestDto;
import ascendion.assessment.dto.response.BorrowBookResponseDto;
import ascendion.assessment.dto.response.PagedResponse;
import ascendion.assessment.dto.response.RegisterBookResponseDto;
import ascendion.assessment.dto.response.ReturnBookResponseDto;
import ascendion.assessment.service.BookService;
import ascendion.assessment.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/books")
@Tag(name = "Books", description = "Operations related to books and borrowing")
public class BookController {

    private final BookService bookService;
    private final BorrowingService borrowingService;

    public BookController(BookService bookService, BorrowingService borrowingService) {
        this.bookService = bookService;
        this.borrowingService = borrowingService;
    }

    @Operation(summary = "Register a new book")
    @PostMapping
    public ResponseEntity<RegisterBookResponseDto> registerNewBook(@RequestBody @Valid RegisterBookRequestDto request) {
        final RegisterBookResponseDto response = bookService.registerNewBook(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all books with pagination and sorting")
    @GetMapping
    public ResponseEntity<PagedResponse<BookSummaryDto>> getAllBooks(Pageable pageable) {
        final Page<BookSummaryDto> page = bookService.getAllBooks(pageable);

        final PagedResponse<BookSummaryDto> response = new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Borrow a book")
    @PostMapping("/borrow")
    public ResponseEntity<BorrowBookResponseDto> borrowBook(@RequestBody @Valid BorrowBookRequestDto request) {
        return ResponseEntity.ok(borrowingService.borrowBook(request));
    }

    @Operation(summary = "Return a borrowed book")
    @PostMapping("/return")
    public ResponseEntity<ReturnBookResponseDto> returnBook(@RequestBody @Valid ReturnBookRequestDto request) {
        return ResponseEntity.ok(borrowingService.returnBook(request));
    }
}
