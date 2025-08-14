package neo.project.task.deal.service;

import java.util.List;
import java.util.UUID;

public interface SendServiceInterface {
    void sendClientDocuments(UUID clientId, List<String> documentIds);
}
