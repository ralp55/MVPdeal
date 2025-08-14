package neo.project.task.deal.service;

import neo.project.task.deal.entity.Statement;

import java.util.List;
import java.util.UUID;

public interface GetAdminStatementIdServiceInterface {
    Statement getStatementById(UUID statementId);
    List<Statement> getAllStatements();
}
