package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "История изменения статуса заявки")
public class StatementStatusHistoryDto {

    @Schema(description = "Статус заявки")
    private ApplicationStatus status;

    @Schema(description = "Время изменения")
    private LocalDateTime time;

    @Schema(description = "Тип изменения статуса")
    private StatusChangeType changeType;
}
