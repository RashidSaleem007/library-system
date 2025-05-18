package ascendion.assessment;

import ascendion.assessment.dto.request.RegisterBorrowerRequestDto;
import ascendion.assessment.dto.response.RegisterBorrowerResponseDto;
import ascendion.assessment.model.Borrower;
import ascendion.assessment.repository.BorrowerRepository;
import ascendion.assessment.service.impl.BorrowerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    @Test
    void registerBorrower_shouldSaveAndReturnResponse() {
        RegisterBorrowerRequestDto request = new RegisterBorrowerRequestDto("Alice", "alice@example.com");
        Borrower saved = new Borrower(1L, "Alice", "alice@example.com");

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(saved);

        RegisterBorrowerResponseDto response = borrowerService.registerBorrower(request);

        assertEquals("Alice", response.name());
        assertEquals("alice@example.com", response.email());
    }
}
