package neo.project.task.deal.Service;

import neo.project.task.deal.DTO.FinishRegistrationRequestDto;

import java.util.UUID;

public interface StatementIDServiceInterface {
    void finishRegistration(UUID statementId, FinishRegistrationRequestDto request);
}
