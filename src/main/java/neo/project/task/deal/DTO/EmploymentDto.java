package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import neo.project.task.deal.dto.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class EmploymentDto {
    @Id
    @GeneratedValue
    @Column(name = "employement_id", columnDefinition = "uuid")
    private UUID employement_id;
    @Schema(description = "Статус занятости", example = "EMPLOYED")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_information")
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

