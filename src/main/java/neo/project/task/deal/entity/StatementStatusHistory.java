package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import neo.project.task.deal.dto.ApplicationStatus;
import neo.project.task.deal.dto.StatusChangeType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "statement_status_history")
@Schema(description = "История изменения статуса заявки")
public class StatementStatusHistory {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statement_status_history")
    private Long id_statement_status_history;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "Статус заявки")
    private ApplicationStatus status;

    @Column(name = "change_time", nullable = false)
    @Schema(description = "Время изменения")
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    @Schema(description = "Тип изменения статуса")
    private StatusChangeType changeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statement_id", nullable = false)
    @Schema(description = "Заявка, к которой относится изменение статуса")
    private Statement statement;
}