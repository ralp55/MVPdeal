package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import neo.project.task.deal.entity.Employment;

import java.time.LocalDate;

@Data
@Schema(description = "Запрос на завершение регистрации")
public class FinishRegistrationRequestDto {

    @Schema(description = "Пол")
    private Gender gender;

    @Schema(description = "Семейное положение")
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцев")
    private Integer dependentAmount;

    @Schema(description = "Дата выдачи паспорта")
    private LocalDate passportIssueDate;

    @Schema(description = "Кем выдан паспорт")
    private String passportIssueBrach;

    @Schema(description = "Информация о занятости")
    private Employment employment;

    @Schema(description = "Номер счета")
    private String accountNumber;
}