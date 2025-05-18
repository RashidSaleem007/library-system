package ascendion.assessment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReturnBookResponseDto(Long borrowingId, Long bookId, LocalDate returnDate) {
}
