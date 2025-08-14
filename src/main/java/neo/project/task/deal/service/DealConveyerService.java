package neo.project.task.deal.service;

import lombok.RequiredArgsConstructor;
import neo.project.task.deal.dto.FullProcessConveyerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealConveyerService {

    private final RestTemplate restTemplate;

    @Value("${services.documents}")
    private String documentsServiceUrl;

    @Value("${services.sign}")
    private String signServiceUrl;

    @Value("${services.code}")
    private String codeServiceUrl;

    public void executeFullProcess(FullProcessConveyerDto request) {
        UUID id = request.getStatementId();

        restTemplate.postForEntity(
                documentsServiceUrl + "/deal/" + id + "/send",
                request.getDocuments(),
                Void.class
        );

        restTemplate.postForEntity(
                signServiceUrl + "/deal/" + id + "/sign",
                request.getSignDocuments(),
                Void.class
        );

        restTemplate.postForEntity(
                codeServiceUrl + "/deal/" + id + "/code?code=" + request.getCode(),
                null,
                Void.class
        );
    }
}
