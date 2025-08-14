package neo.project.task.deal.service;

import java.util.UUID;

public interface CodeServiceInterface {
    void confirmStatement(UUID statementId, String verificationCode);
}
