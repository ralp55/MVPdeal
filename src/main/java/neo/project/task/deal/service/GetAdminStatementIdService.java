package neo.project.task.deal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.repository.StatementRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAdminStatementIdService {

    private final StatementRepository statementRepository;

    @Transactional
    public Statement getStatementById(UUID statementId) {
        log.info("Fetching statement with ID: {}", statementId);

        return statementRepository.findById(statementId)
                .orElseThrow(() -> {
                    log.error("Statement not found with ID: {}", statementId);
                    return new ResourceNotFoundException("Statement not found with ID: " + statementId);
                });
    }
    @Transactional
    public List<Statement> getAllStatements() {
        return statementRepository.findAll();
    }
}
