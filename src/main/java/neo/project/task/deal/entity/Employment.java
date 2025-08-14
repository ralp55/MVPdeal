package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import neo.project.task.deal.dto.EmploymentStatus;
import neo.project.task.deal.dto.Position;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Employment {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "employement_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID employement_id;

    @Schema(description = "Статус занятости", example = "EMPLOYED")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_information", nullable = false)
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН работодателя", example = "1234567890")
    private String employerINN;

    @Schema(description = "Ежемесячная зарплата", example = "100000")
    private BigDecimal salary;

    @Schema(description = "Должность", example = "MID_MANAGER")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "position_info")
    private Position position;

    @Schema(description = "Общий стаж (в месяцах)", example = "120")
    private Integer workExperienceTotal;

    @Schema(description = "Стаж на текущем месте (в месяцах)", example = "24")
    private Integer workExperienceCurrent;
}

