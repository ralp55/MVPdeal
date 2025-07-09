package neo.project.task.deal.service;

import neo.project.task.deal.dto.FinishRegistrationRequestDto;

import java.util.UUID;

public interface StatementIDServiceInterface {
    void finishRegistration(UUID statementId, FinishRegistrationRequestDto request);
}
