package ascendion.assessment.service;

import ascendion.assessment.dto.request.RegisterBorrowerRequestDto;
import ascendion.assessment.dto.response.RegisterBorrowerResponseDto;

public interface BorrowerService {
    RegisterBorrowerResponseDto registerBorrower(RegisterBorrowerRequestDto request);
}
