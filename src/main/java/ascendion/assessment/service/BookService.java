package ascendion.assessment.service;

import ascendion.assessment.dto.BookSummaryDto;
import ascendion.assessment.dto.request.RegisterBookRequestDto;
import ascendion.assessment.dto.response.RegisterBookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    RegisterBookResponseDto registerNewBook(final RegisterBookRequestDto request);

    Page<BookSummaryDto> getAllBooks(Pageable pageable);
}
