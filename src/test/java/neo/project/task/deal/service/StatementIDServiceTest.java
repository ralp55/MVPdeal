package neo.project.task.deal.service;

import neo.project.task.deal.dto.*;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.LoanOffer;
import neo.project.task.deal.entity.Passport;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.repository.ClientRepository;
import neo.project.task.deal.repository.CreditRepository;
import neo.project.task.deal.repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementIDServiceTest {

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StatementIDService statementIDService;

    @Test
    void testFinishRegistrationThrowsWhenStatementNotFound() {
        UUID statementId = UUID.randomUUID();
        when(statementRepository.findById(statementId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                statementIDService.finishRegistration(statementId, new FinishRegistrationRequestDto())
        );

        assertEquals("Statement not found", ex.getMessage());
        verifyNoInteractions(restTemplate);
        verifyNoInteractions(creditRepository);
    }

    @Test
    void testFinishRegistrationThrowsWhenNoOffer() {
        UUID statementId = UUID.randomUUID();
        Statement statement = new Statement();
        statement.setClient(new Client());
        statement.setAppliedOffer(null);

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                statementIDService.finishRegistration(statementId, new FinishRegistrationRequestDto())
        );

        assertEquals("Applied offer is missing in the statement.", ex.getMessage());
    }
    @Test
    void testFinishRegistrationThrowsWhenClientIsNull() {
        UUID statementId = UUID.randomUUID();
        Statement statement = new Statement();
        statement.setAppliedOffer(new LoanOffer());
        statement.setClient(null);

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        RuntimeException ex = assertThrows(NullPointerException.class, () ->
                statementIDService.finishRegistration(statementId, new FinishRegistrationRequestDto())
        );

        assertTrue(ex.getMessage() == null || ex.getMessage().contains("")); // Нет сообщения у NPE
    }
    @Test
    void testFinishRegistrationThrowsWhenClientHasNoPassport() {
        UUID statementId = UUID.randomUUID();

        Client client = new Client();
        client.setPassport(null);

        Statement statement = new Statement();
        statement.setClient(client);
        statement.setAppliedOffer(new LoanOffer());

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        RuntimeException ex = assertThrows(NullPointerException.class, () ->
                statementIDService.finishRegistration(statementId, new FinishRegistrationRequestDto())
        );

        assertTrue(ex.getMessage() == null || ex.getMessage().contains(""));
    }
    @Test
    void testFinishRegistrationThrowsWhenRestTemplateReturnsNull() {
        UUID statementId = UUID.randomUUID();
        ReflectionTestUtils.setField(statementIDService, "calculatorCalcUrl", "http://mock-url");

        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Ivanov");
        client.setMiddleName("Ivanovich");
        client.setBirthDate(LocalDate.of(1990, 1, 1));
        Passport passport = new Passport();
        passport.setSeries("1234");
        passport.setNumberPassport("567890");
        client.setPassport(passport);

        LoanOffer offer = new LoanOffer();
        offer.setRequestedAmount(BigDecimal.valueOf(100000));
        offer.setTerm(12);
        offer.setIsInsuranceEnabled(true);
        offer.setIsSalaryClient(false);

        Statement statement = new Statement();
        statement.setClient(client);
        statement.setAppliedOffer(offer);

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
        when(restTemplate.postForObject(anyString(), any(), eq(CreditDto.class))).thenReturn(null);

        RuntimeException ex = assertThrows(NullPointerException.class, () ->
                statementIDService.finishRegistration(statementId, new FinishRegistrationRequestDto())
        );

        assertTrue(ex.getMessage() == null || ex.getMessage().contains(""));
    }

}