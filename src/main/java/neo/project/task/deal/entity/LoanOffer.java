package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "loan_offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Кредитное предложение (сущность)")
public class LoanOffer {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_LoanOffer")
    @Schema(description = "ID кредитного предложения")
    private Long id_LoanOffer;

    @Column(name = "statement_id", nullable = false)
    @Schema(description = "ID заявления")
    private UUID statementId;

    @Column(name = "requested_amount", nullable = false)
    @Schema(description = "Запрошенная сумма")
    private BigDecimal requestedAmount;

    @Column(name = "total_amount", nullable = false)
    @Schema(description = "Общая сумма к возврату")
    private BigDecimal totalAmount;

    @Column(name = "term", nullable = false)
    @Schema(description = "Срок кредита (месяцы)")
    private Integer term;

    @Column(name = "monthly_payment", nullable = false)
    @Schema(description = "Ежемесячный платёж")
    private BigDecimal monthlyPayment;

    @Column(name = "rate", nullable = false)
    @Schema(description = "Процентная ставка")
    private BigDecimal rate;

    @Column(name = "insurance_enabled", nullable = false)
    @Schema(description = "Включено страхование")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client", nullable = false)
    @Schema(description = "Является зарплатным клиентом")
    private Boolean isSalaryClient;
}
