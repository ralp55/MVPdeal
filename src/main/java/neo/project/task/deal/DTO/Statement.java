package neo.project.task.deal.dto;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import neo.project.task.deal.converter.LoanOfferDtoConverter;
import neo.project.task.deal.converter.StatementStatusHistoryConverter;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import jakarta.persistence.Convert;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "statement")
@Schema(description = "Заявка")
public class Statement {

    @Id
    @GeneratedValue
    @Column(name = "statement_id", columnDefinition = "uuid")
    @Schema(description = "ID заявки")
    private UUID statementId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Schema(description = "Клиент")
    private Client client;

    @Column(name = "credit_id")
    @Schema(description = "ID кредита")
    private UUID creditId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Schema(description = "Текущий статус заявки")
    private ApplicationStatus status;

    @Column(name = "creation_date")
    @Schema(description = "Дата создания заявки")
    private LocalDateTime creationDate;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @Schema(description = "Выбранное кредитное предложение")
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    @Schema(description = "Дата подписания")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    @Schema(description = "СЭС-код")
    private String sesCode;

    @Type(JsonType.class)
    @Column(name = "status_history", columnDefinition = "jsonb")
    @Schema(description = "История изменения статусов")
    private List<StatementStatusHistoryDto> statusHistory;
}

