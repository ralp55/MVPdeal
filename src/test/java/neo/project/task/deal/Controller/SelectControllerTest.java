package neo.project.task.deal.Controller;

import neo.project.task.deal.DTO.LoanOfferDto;
import neo.project.task.deal.Service.SelectServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SelectControllerTest {

    @Mock
    private SelectServiceInterface statementService;

    @InjectMocks
    private SelectController controller;

    private final LoanOfferDto offer = new LoanOfferDto();

    @BeforeEach
    void setup() {
        offer.setStatementId(UUID.randomUUID());
    }

    @Test
    void testSelectLoanOfferSuccess() {
        ResponseEntity<Void> response = controller.selectLoanOffer(offer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(statementService).applyLoanOffer(offer);
    }

    @Test
    void testSelectLoanOfferThrowsException() {
        doThrow(new RuntimeException("Statement not found")).when(statementService).applyLoanOffer(any());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.selectLoanOffer(offer)
        );

        assertEquals("Statement not found", exception.getMessage());
    }
}
