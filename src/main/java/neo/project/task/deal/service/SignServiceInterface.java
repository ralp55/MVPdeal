package neo.project.task.deal.service;

import java.util.List;
import java.util.UUID;

public interface SignServiceInterface {
    void sendForSigning(UUID clientId, List<String> documentReferences);
}
