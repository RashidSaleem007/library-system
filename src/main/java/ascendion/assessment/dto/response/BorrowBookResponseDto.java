package ascendion.assessment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response after borrowing a book")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BorrowBookResponseDto(
        @Schema(description = "Borrowing transaction ID", example = "100")
        Long borrowingId,
        @Schema(description = "ID of the book to borrow", example = "5")
        Long bookId,
        @Schema(description = "Title of the book", example = "Clean Code")
        String bookTitle,
        @Schema(description = "Author of the book", example = "Rashid Saleem")
        String bookAuthor,
        @Schema(description = "ISBN number of the book", example = "978-3-16-148410-0")
        String bookIsbn,
        @Schema(description = "ID of the borrower", example = "2")
        Long borrowerId,
        String borrowerName,
        @Schema(description = "Borrow date")
        LocalDate borrowDate) {
}
