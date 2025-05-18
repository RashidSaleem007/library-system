package ascendion.assessment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterBookResponseDto(Long id, String isbn, String title, String author) {
}
