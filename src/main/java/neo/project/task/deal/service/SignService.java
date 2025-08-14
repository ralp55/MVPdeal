package neo.project.task.deal.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.text.Document;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {

    private final ClientRepository clientRepository;
    private final WebClient webClient;

    @Value("${esign.service.url}")
    private String esignServiceUrl;

    public void sendForSigning(UUID clientId, List<String> documentReferences) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        SigningRequest request = new SigningRequest(
                documentReferences,
                client.getEmail(),
                formatClientName(client),
                generateSigningToken()
        );

        sendToEsignService(request);
    }

    private void sendToEsignService(SigningRequest request) {
        try {
            webClient.post()
                    .uri(esignServiceUrl + "/sign-requests")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            Mono.error(new RuntimeException("Esign service error")))
                    .toBodilessEntity()
                    .block();

            log.info("Documents sent for signing to {}", request.getRecipientEmail());
        } catch (Exception e) {
            log.error("Error sending to esign service", e);
            throw new RuntimeException("Esign service unavailable");
        }
    }

    private String formatClientName(Client client) {
        return String.format("%s %s", client.getLastName(), client.getFirstName());
    }

    private String generateSigningToken() {
        return UUID.randomUUID().toString();
    }

    @Data
    @AllArgsConstructor
    private static class SigningRequest {
        private List<String> documentReferences;
        private String recipientEmail;
        private String clientName;
        private String signingToken;
    }
}