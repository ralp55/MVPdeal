package neo.project.task.deal.service;

import neo.project.task.deal.dto.LoanOfferDto;
import neo.project.task.deal.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementServiceInterface {
    List<LoanOfferDto> processStatementRequest(LoanStatementRequestDto request);
}
