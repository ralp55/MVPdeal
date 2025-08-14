package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
@Schema(description = "Информация используемая в паттерне gateway")
public class FullProcessConveyerDto {
    private UUID statementId;
    private List<String> documents;
    private List<String> signDocuments;
    private String code;

}
