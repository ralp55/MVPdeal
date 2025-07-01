package neo.project.task.deal.Service;

import lombok.RequiredArgsConstructor;
import neo.project.task.deal.DTO.*;
import neo.project.task.deal.Repository.ClientRepository;
import neo.project.task.deal.Repository.PassportRepository;
import neo.project.task.deal.Repository.StatementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService implements StatementServiceInterface {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final PassportRepository passportRepository;
    private final RestTemplate restTemplate;

    @Value("${calculator.offers.url}")
    private String calculatorOfferUrl;

    @Override
    public List<LoanOfferDto> processStatementRequest(LoanStatementRequestDto request) {
        Optional<Passport> optionalPassport = passportRepository
                .findBySeriesAndNumberPassport(request.getPassportSeries(), request.getPassportNumber());

        if (optionalPassport.isEmpty()) {
            throw new RuntimeException("Паспорт не найден: серия " + request.getPassportSeries() +
                    ", номер " + request.getPassportNumber());
        }

        Passport passport = optionalPassport.get();

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setMiddleName(request.getMiddleName());
        client.setBirthDate(request.getBirthdate());
        client.setEmail(request.getEmail());
        client.setPassport(passport);
        client = clientRepository.save(client);

        Statement statement = new Statement();
        statement.setClient(client);
        statement.setCreationDate(LocalDateTime.now());
        statement.setStatus(ApplicationStatus.PREAPPROVAL);
        statement = statementRepository.save(statement);

        ResponseEntity<LoanOfferDto[]> response = restTemplate.postForEntity(
                calculatorOfferUrl,
                request,
                LoanOfferDto[].class
        );

        List<LoanOfferDto> offers = Arrays.asList(Objects.requireNonNull(response.getBody()));

        UUID statementId = statement.getStatementId();
        offers.forEach(offer -> offer.setStatementId(statementId));

        return offers;
    }
}

