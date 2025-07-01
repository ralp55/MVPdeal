package neo.project.task.deal.Service;

import lombok.RequiredArgsConstructor;
import neo.project.task.deal.Repository.CreditRepository;
import org.springframework.beans.factory.annotation.Value;
import neo.project.task.deal.DTO.*;
import neo.project.task.deal.Repository.ClientRepository;
import neo.project.task.deal.Repository.StatementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementIDService implements StatementIDServiceInterface {

    private final StatementRepository statementRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RestTemplate restTemplate;

    @Value("${calculator.calc.url}")
    private String calculatorCalcUrl;

    @Override
    public void finishRegistration(UUID statementId, FinishRegistrationRequestDto request) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        Client client = statement.getClient();

        LoanOfferDto offer = statement.getAppliedOffer();
        if (offer == null) {
            throw new RuntimeException("Applied offer is missing in the statement.");
        }

        ScoringDataDto scoringData = new ScoringDataDto();
        scoringData.setAmount(offer.getRequestedAmount());
        scoringData.setTerm(offer.getTerm());
        scoringData.setFirstName(client.getFirstName());
        scoringData.setLastName(client.getLastName());
        scoringData.setMiddleName(client.getMiddleName());
        scoringData.setGender(request.getGender());
        scoringData.setBirthdate(client.getBirthDate());
        scoringData.setPassportSeries(client.getPassport().getSeries());
        scoringData.setPassportNumber(client.getPassport().getNumberPassport());
        scoringData.setPassportIssueDate(request.getPassportIssueDate());
        scoringData.setPassportIssueBranch(request.getPassportIssueBrach());
        scoringData.setMaritalStatus(request.getMaritalStatus());
        scoringData.setDependentAmount(request.getDependentAmount());
        scoringData.setEmployment(request.getEmployment());
        scoringData.setAccountNumber(request.getAccountNumber());
        scoringData.setIsInsuranceEnabled(offer.getIsInsuranceEnabled());
        scoringData.setIsSalaryClient(offer.getIsSalaryClient());

        CreditDto creditDto = restTemplate.postForObject(calculatorCalcUrl, scoringData, CreditDto.class);

        Credit credit = new Credit();
        credit.setAmount(creditDto.getAmount());
        credit.setTerm(creditDto.getTerm());
        credit.setMonthlyPayment(creditDto.getMonthlyPayment());
        credit.setRate(creditDto.getRate());
        credit.setPsk(creditDto.getPsk());
        credit.setPaymentSchedule(creditDto.getPaymentSchedule());
        credit.setInsuranceEnabled(creditDto.getIsInsuranceEnabled());
        credit.setSalaryClient(creditDto.getIsSalaryClient());
        credit.setCreditStatus(CreditStatusInformation.CALCULATED);

        credit = creditRepository.save(credit);

        statement.setCreditId(credit.getCreditId());
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(new StatementStatusHistoryDto(
                ApplicationStatus.CC_APPROVED,
                LocalDateTime.now(),
                StatusChangeType.AUTO
        ));

        statementRepository.save(statement);
    }
}
