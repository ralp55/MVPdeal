package neo.project.task.deal.Service;

import jakarta.persistence.EntityNotFoundException;
import neo.project.task.deal.DTO.ApplicationStatus;
import neo.project.task.deal.DTO.LoanOfferDto;
import neo.project.task.deal.DTO.Statement;
import neo.project.task.deal.Repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectServiceTest {

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private SelectService selectService;

    @Test
    void testApplyLoanOfferSuccessfully() {
        UUID statementId = UUID.randomUUID();

        LoanOfferDto offer = new LoanOfferDto();
        offer.setStatementId(statementId);
        offer.setTerm(12);
        offer.setRate(BigDecimal.valueOf(10.5));
        offer.setIsSalaryClient(true);
        offer.setIsInsuranceEnabled(true);
        offer.setTotalAmount(BigDecimal.valueOf(100000.0));
        offer.setMonthlyPayment(BigDecimal.valueOf(8500.0));
        offer.setRequestedAmount(BigDecimal.valueOf(100000.0));

        Statement statement = new Statement();
        statement.setStatementId(statementId);
        statement.setStatusHistory(new ArrayList<>());

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        selectService.applyLoanOffer(offer);

        verify(statementRepository).save(argThat(saved ->
                saved.getStatus() == ApplicationStatus.APPROVED &&
                        saved.getAppliedOffer().equals(offer) &&
                        saved.getStatusHistory().size() == 1 &&
                        saved.getStatusHistory().get(0).getStatus() == ApplicationStatus.APPROVED
        ));
    }

    @Test
    void testApplyLoanOfferThrowsExceptionWhenStatementNotFound() {
        UUID statementId = UUID.randomUUID();
        LoanOfferDto offer = new LoanOfferDto();
        offer.setStatementId(statementId);

        when(statementRepository.findById(statementId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                selectService.applyLoanOffer(offer)
        );

        assertEquals("Statement not found with id: " + statementId, ex.getMessage());
        verify(statementRepository, never()).save(any());
    }
}
