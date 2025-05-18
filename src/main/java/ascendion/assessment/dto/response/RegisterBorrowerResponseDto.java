package ascendion.assessment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterBorrowerResponseDto(Long id, String name, String email) {
}
