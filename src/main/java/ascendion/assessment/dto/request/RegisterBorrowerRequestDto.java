package ascendion.assessment.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterBorrowerRequestDto(
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
