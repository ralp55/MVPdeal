package neo.project.task.deal.Service;

import jakarta.persistence.EntityNotFoundException;
import neo.project.task.deal.DTO.*;
import neo.project.task.deal.Repository.StatementRepository;
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

    @Override
    public void applyLoanOffer(LoanOfferDto offer) {
        UUID statementId = offer.getStatementId();
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new EntityNotFoundException("Statement not found with id: " + statementId));

        statement.setStatus(ApplicationStatus.APPROVED);

        List<StatementStatusHistoryDto> history = Optional.ofNullable(statement.getStatusHistory())
                .orElse(new ArrayList<>());

        StatementStatusHistoryDto statusChange = new StatementStatusHistoryDto();
        statusChange.setStatus(ApplicationStatus.APPROVED);
        statusChange.setTime(LocalDateTime.now());
        statusChange.setChangeType(StatusChangeType.AUTO);

        history.add(statusChange);
        statement.setStatusHistory(history);

        statement.setAppliedOffer(offer);

        statementRepository.save(statement);
    }
}