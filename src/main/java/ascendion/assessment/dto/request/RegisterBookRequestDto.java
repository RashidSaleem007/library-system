package ascendion.assessment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to register a new book")
public record RegisterBookRequestDto(
        @Schema(description = "ISBN number of the book", example = "978-3-16-148410-0")
        @NotBlank String isbn,
        @Schema(description = "Title of the book", example = "Clean Code")
        @NotBlank String title,
        @Schema(description = "Author of the book", example = "Rashid Saleem")
        @NotBlank String author) {
}
