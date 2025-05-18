package ascendion.assessment.service.impl;

import ascendion.assessment.dto.request.RegisterBorrowerRequestDto;
import ascendion.assessment.dto.response.RegisterBorrowerResponseDto;
import ascendion.assessment.model.Borrower;
import ascendion.assessment.repository.BorrowerRepository;
import ascendion.assessment.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }


    @Override
    public RegisterBorrowerResponseDto registerBorrower(RegisterBorrowerRequestDto request) {
        log.info("RegisterBorrowerRequestDto {}:", request);

        final Borrower borrower = new Borrower();
        borrower.setName(request.name());
        borrower.setEmail(request.email());

        log.info("saving borrower {}:", borrower);
        final Borrower saved = borrowerRepository.save(borrower);
        return new RegisterBorrowerResponseDto(saved.getId(), saved.getName(), saved.getEmail());
    }
}
