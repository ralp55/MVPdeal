package neo.project.task.deal.service;

import lombok.RequiredArgsConstructor;
import neo.project.task.deal.entity.*;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.Credit;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.mapper.CreditMapper;
import neo.project.task.deal.mapper.ScoringDataMapper;
import neo.project.task.deal.mapper.StatementMapper;
import neo.project.task.deal.mapper.StatementStatusHistoryMapper;
import neo.project.task.deal.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Value;
import neo.project.task.deal.dto.*;
import neo.project.task.deal.repository.ClientRepository;
import neo.project.task.deal.repository.StatementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementIDService implements StatementIDServiceInterface {

    private final StatementRepository statementRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final WebClient webClient;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditMapper creditMapper;
    private final StatementStatusHistoryMapper StatementStatusHistoryMapper;
    private final StatementMapper statementMapper;

    @Value("${calculator.calc.url}")
    private String calculatorCalcUrl;

    @Override
    public void finishRegistration(UUID statementId, FinishRegistrationRequestDto request) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        Client client = statement.getClient();

        LoanOffer offer = statement.getAppliedOffer();
        if (offer == null) {
            throw new RuntimeException("Applied offer is missing in the statement.");
        }

        ScoringDataDto scoringData = scoringDataMapper.toScoringDataDto(client, offer, request);

        CreditDto creditDto = webClient
                .post()
                .uri(calculatorCalcUrl)
                .bodyValue(scoringData)
                .retrieve()
                .bodyToMono(CreditDto.class)
                .block();

        Credit credit = creditMapper.toCredit(creditDto);
        credit = creditRepository.save(credit);

        statementMapper.updateStatement(statement, credit.getCreditId(), ApplicationStatus.CC_APPROVED);
        StatementStatusHistory statusHistory = StatementStatusHistoryMapper.toStatementStatusHistory(statement, ApplicationStatus.CC_APPROVED);
        if (statement.getStatusHistory() == null) {
            statement.setStatusHistory(new ArrayList<>());
        }
        statement.getStatusHistory().add(statusHistory);
        statementRepository.save(statement);
    }
}
