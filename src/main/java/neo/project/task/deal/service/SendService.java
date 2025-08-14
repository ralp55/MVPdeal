package neo.project.task.deal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.ClientDTO;
import neo.project.task.deal.dto.LoanStatementRequestDto;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendService {

    private final WebClient webClient;
    private final ClientRepository clientRepository;

    @Value("${document.delivery.service.url}")
    private String documentDeliveryUrl;

    public void sendClientDocuments(UUID clientId, List<String> documentId) {
        ClientDTO client = clientRepository.findClientDtoById(clientId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден: " + clientId));


        DocumentDeliveryRequest request = new DocumentDeliveryRequest(
                documentId,
                client.getEmail(),
                formatClientName(client)
        );

        webClient.post()
                .uri(documentDeliveryUrl)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

        log.info("Документы отправлены клиенту {} ({})", clientId, client.getEmail());
    }

    private String formatClientName(ClientDTO client) {
        return String.format("%s %s %s",
                client.getLastName(),
                client.getFirstName(),
                client.getMiddleName());
    }

    private record DocumentDeliveryRequest(
            List<String> documentIds,
            String recipientEmail,
            String clientFullName
    ) {}
}