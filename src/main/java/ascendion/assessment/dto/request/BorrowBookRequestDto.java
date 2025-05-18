package ascendion.assessment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to borrow a book copy")
public record BorrowBookRequestDto(
        @Schema(description = "ID of the book to borrow", example = "5")
        @NotNull Long bookId,
        @Schema(description = "ID of the borrower", example = "2")
        @NotNull Long borrowerId) {
}