package neo.project.task.deal.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import neo.project.task.deal.dto.*;
import neo.project.task.deal.entity.LoanOffer;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.entity.StatementStatusHistory;
import neo.project.task.deal.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SelectService implements SelectServiceInterface {

    @Autowired
    private StatementRepository statementRepository;

    @Transactional
    @Override
    public void applyLoanOffer(LoanOfferDto offer) {
        UUID statementId = offer.getStatementId();
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new EntityNotFoundException("Statement not found with id: " + statementId));

        statement.setStatus(ApplicationStatus.APPROVED);

        List<StatementStatusHistory> history = Optional.ofNullable(statement.getStatusHistory())
                .orElse(new ArrayList<>());

        StatementStatusHistory statusChange = new StatementStatusHistory();
        statusChange.setStatus(ApplicationStatus.APPROVED);
        statusChange.setTime(LocalDateTime.now());
        statusChange.setChangeType(StatusChangeType.AUTO);

        history.add(statusChange);
        statement.setStatusHistory(history);

        statement.setAppliedOffer(mapToEntity(offer));

        statementRepository.save(statement);
    }
    private LoanOffer mapToEntity(LoanOfferDto dto) {
        return LoanOffer.builder()
                .statementId(dto.getStatementId())
                .requestedAmount(dto.getRequestedAmount())
                .totalAmount(dto.getTotalAmount())
                .term(dto.getTerm())
                .monthlyPayment(dto.getMonthlyPayment())
                .rate(dto.getRate())
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();
    }

}