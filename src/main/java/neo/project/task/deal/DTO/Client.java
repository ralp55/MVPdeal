package neo.project.task.deal.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "client")
@Schema(description = "Клиент")
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "client_id", columnDefinition = "uuid")
    @Schema(description = "ID клиента")
    private UUID clientId;

    @Column(name = "last_name")
    @Schema(description = "Фамилия")
    private String lastName;

    @Column(name = "first_name")
    @Schema(description = "Имя")
    private String firstName;

    @Column(name = "middle_name")
    @Schema(description = "Отчество")
    private String middleName;

    @Column(name = "birth_date")
    @Schema(description = "Дата рождения")
    private LocalDate birthDate;

    @Column(name = "email")
    @Schema(description = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    @Schema(description = "Пол")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marual_status")
    @Schema(description = "Семейное положение")
    private MaritalStatus marualStatus;

    @Column(name = "dependent_amount")
    @Schema(description = "Количество иждивенцев")
    private Integer dependentAmount;

    @ManyToOne
    @JoinColumn(name = "passport_id")
    @Schema(description = "Паспорт клиента")
    private Passport passport;

    @ManyToOne
    @JoinColumn(name = "employment_id")
    @Schema(description = "Информация о занятости")
    private EmploymentDto employment;

    @Column(name = "account_number")
    @Schema(description = "Номер счета")
    private String accountNumber;
}