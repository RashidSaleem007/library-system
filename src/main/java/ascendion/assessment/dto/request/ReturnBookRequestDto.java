package ascendion.assessment.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReturnBookRequestDto(
        @NotNull Long bookId,
        @NotNull Long borrowerId) {
}
