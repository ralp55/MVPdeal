package neo.project.task.deal.Service;

import neo.project.task.deal.DTO.*;
import neo.project.task.deal.Repository.ClientRepository;
import neo.project.task.deal.Repository.PassportRepository;
import neo.project.task.deal.Repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.PotentialStubbingProblem;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StatementService statementService;

    @Test
    void testProcessStatementRequest_PassportNotFound() {
        // Arrange
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        request.setPassportSeries("1234");
        request.setPassportNumber("567890");

        when(passportRepository.findBySeriesAndNumberPassport("1234", "567890"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                statementService.processStatementRequest(request)
        );

        assertTrue(ex.getMessage().contains("Паспорт не найден"));
        verify(passportRepository).findBySeriesAndNumberPassport("1234", "567890");
        verifyNoInteractions(clientRepository, statementRepository, restTemplate);
    }

    @Test
    void testProcessStatementRequest_NullResponseBodyThrowsException() {
        // Arrange
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        request.setPassportSeries("1234");
        request.setPassportNumber("567890");

        Passport passport = new Passport();
        passport.setPassportId(UUID.randomUUID());

        when(passportRepository.findBySeriesAndNumberPassport("1234", "567890"))
                .thenReturn(Optional.of(passport));
        when(clientRepository.save(any())).thenReturn(new Client());
        when(statementRepository.save(any())).thenReturn(new Statement());
        when(restTemplate.postForEntity(anyString(), eq(request), eq(LoanOfferDto[].class)))
                .thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        assertThrows(PotentialStubbingProblem.class, () ->
                statementService.processStatementRequest(request)
        );
    }


}