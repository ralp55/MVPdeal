package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import neo.project.task.deal.dto.CreditStatusInformation;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о кредите")
public class Credit {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "credit_id", columnDefinition = "uuid", updatable = false, nullable = false)
    @Schema(description = "ID кредита")
    private UUID creditId;

    @Column(name = "amount")
    @Schema(description = "Сумма кредита")
    private BigDecimal amount;

    @Column(name = "term")
    @Schema(description = "Срок кредита (в месяцах)")
    private Integer term;

    @Column(name = "monthly_payment")
    @Schema(description = "Ежемесячный платеж")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    @Schema(description = "Процентная ставка")
    private BigDecimal rate;

    @Column(name = "psk")
    @Schema(description = "Полная стоимость кредита (ПСК)")
    private BigDecimal psk;

    @Column(name = "payment_shedule", columnDefinition = "jsonb")
    @Schema(description = "График платежей")
    private String paymentSchedule;

    @Column(name = "insurance_enabled")
    @Schema(description = "Страховка включена")
    private Boolean insuranceEnabled;

    @Column(name = "salary_client")
    @Schema(description = "Является ли клиентом с зарплатным проектом")
    private Boolean salaryClient;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    @Schema(description = "Статус кредита")
    private CreditStatusInformation creditStatus;
}
