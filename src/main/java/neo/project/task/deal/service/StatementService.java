package neo.project.task.deal.service;

import lombok.RequiredArgsConstructor;
import neo.project.task.deal.dto.*;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.Passport;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.mapper.*;
import neo.project.task.deal.repository.ClientRepository;
import neo.project.task.deal.repository.PassportRepository;
import neo.project.task.deal.repository.StatementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final WebClient webClient;
    private final ClientMapper ClientMapper;
    private final StatementMapper StatementMapper;

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

        Client client = ClientMapper.toClient(request, passport);
        client = clientRepository.save(client);

        Statement statement = StatementMapper.toStatement(client);
        statement = statementRepository.save(statement);


        LoanOfferDto[] responseBody = webClient
                .post()
                .uri(calculatorOfferUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LoanOfferDto[].class)
                .block();

        List<LoanOfferDto> offers = Arrays.asList(Objects.requireNonNull(responseBody));

        UUID statementId = statement.getStatementId();
        offers.forEach(offer -> offer.setStatementId(statementId));

        return offers;
    }
}

