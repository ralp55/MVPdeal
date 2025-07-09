package neo.project.task.deal.controller;

import neo.project.task.deal.dto.LoanOfferDto;
import neo.project.task.deal.dto.LoanStatementRequestDto;
import neo.project.task.deal.service.StatementServiceInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Mock
    private StatementServiceInterface statementService;

    @InjectMocks
    private StatementController controller;

    private final LoanStatementRequestDto request = new LoanStatementRequestDto();

    @Test
    void testGetLoanOffersSuccess() {
        List<LoanOfferDto> expectedOffers = List.of(new LoanOfferDto(), new LoanOfferDto());
        when(statementService.processStatementRequest(any())).thenReturn(expectedOffers);

        ResponseEntity<List<LoanOfferDto>> response = controller.getLoanOffers(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOffers.size(), response.getBody().size());
        verify(statementService).processStatementRequest(request);
    }

    @Test
    void testGetLoanOffersThrowsException() {
        when(statementService.processStatementRequest(any())).thenThrow(new RuntimeException("Invalid input"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.getLoanOffers(request)
        );

        assertEquals("Invalid input", exception.getMessage());
    }
}
