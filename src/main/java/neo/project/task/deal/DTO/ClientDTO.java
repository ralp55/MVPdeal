package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "client")
@Schema(description = "Клиент")
public class ClientDTO {

    @Id
    @GeneratedValue
    @Schema(description = "ID клиента")
    private UUID clientId;

    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Отчество")
    private String middleName;

    @Schema(description = "Дата рождения")
    private LocalDate birthDate;

    @Schema(description = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Пол")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Семейное положение")
    private MaritalStatus marualStatus;

    @Schema(description = "Количество иждивенцев")
    private Integer dependentAmount;

    @Schema(description = "Паспорт клиента")
    private Passport passport;

    @Schema(description = "Информация о занятости")
    private EmploymentDto employment;

    @Schema(description = "Номер счета")
    private String accountNumber;
}