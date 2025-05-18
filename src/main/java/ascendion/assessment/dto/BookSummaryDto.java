package ascendion.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary details of a book")
public record BookSummaryDto(
        @Schema(description = "Unique identifier of the book", example = "1")
        Long id,
        @Schema(description = "ISBN number of the book", example = "978-3-16-148410-0")
        String isbn,
        @Schema(description = "Title of the book", example = "Clean Code")
        String title,
        @Schema(description = "Author of the book", example = "Rashid Saleem")
        String author) {
}
