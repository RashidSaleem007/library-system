package ascendion.assessment.service;

import ascendion.assessment.dto.request.BorrowBookRequestDto;
import ascendion.assessment.dto.request.ReturnBookRequestDto;
import ascendion.assessment.dto.response.BorrowBookResponseDto;
import ascendion.assessment.dto.response.ReturnBookResponseDto;

public interface BorrowingService {
    BorrowBookResponseDto borrowBook(final BorrowBookRequestDto borrowBookRequestDto);

    ReturnBookResponseDto returnBook(final ReturnBookRequestDto returnBookRequestDto);
}
