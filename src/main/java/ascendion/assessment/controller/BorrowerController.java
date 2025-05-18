package ascendion.assessment.controller;


import ascendion.assessment.dto.request.RegisterBorrowerRequestDto;
import ascendion.assessment.dto.response.RegisterBorrowerResponseDto;
import ascendion.assessment.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping
    public ResponseEntity<RegisterBorrowerResponseDto> registerBorrower(@RequestBody @Valid RegisterBorrowerRequestDto request) {
        final RegisterBorrowerResponseDto response = borrowerService.registerBorrower(request);
        return ResponseEntity.ok(response);
    }
}
