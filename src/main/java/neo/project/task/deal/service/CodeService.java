package neo.project.task.deal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.ApplicationStatus;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.entity.StatementStatusHistory;
import neo.project.task.deal.repository.ClientRepository;
import neo.project.task.deal.repository.StatementRepository;
import neo.project.task.deal.repository.StatementStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {

    private final StatementRepository statementRepository;
    private final StatementStatusHistoryRepository statusHistoryRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public void confirmStatement(UUID statementId, String verificationCode) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        statement.setStatus(ApplicationStatus.APPROVED);
        statement.setSignDate(LocalDateTime.now());

        StatementStatusHistory history = new StatementStatusHistory();
        history.setStatement(statement);
        history.setStatus(ApplicationStatus.APPROVED);
        history.setTime(LocalDateTime.now());
        statusHistoryRepository.save(history);

        statementRepository.save(statement);

        log.info("Statement {} confirmed with code {}", statementId, verificationCode);
    }

    @Transactional
    public void rejectStatement(UUID statementId, String verificationCode) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        if (!verificationCode.equals(statement.getSesCode())) {
            throw new RuntimeException("Invalid verification code");
        }

        statement.setStatus(ApplicationStatus.CC_DENIED);

        StatementStatusHistory history = new StatementStatusHistory();
        history.setStatement(statement);
        history.setStatus(ApplicationStatus.CC_DENIED);
        history.setTime(LocalDateTime.now());
        statusHistoryRepository.save(history);

        statementRepository.save(statement);

        log.info("Statement {} rejected with code {}", statementId, verificationCode);
    }

    public String generateNewCode(UUID statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        String newCode = generateRandomCode();
        statement.setSesCode(newCode);
        statementRepository.save(statement);

        return newCode;
    }

    private String generateRandomCode() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
