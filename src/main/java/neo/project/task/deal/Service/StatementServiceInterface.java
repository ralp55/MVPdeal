package neo.project.task.deal.Service;

import neo.project.task.deal.DTO.LoanOfferDto;
import neo.project.task.deal.DTO.LoanStatementRequestDto;

import java.util.List;

public interface StatementServiceInterface {
    List<LoanOfferDto> processStatementRequest(LoanStatementRequestDto request);
}
