package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import neo.project.task.deal.dto.Gender;
import neo.project.task.deal.dto.MaritalStatus;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "client")
@Schema(description = "Клиент")
public class Client {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "client_id", columnDefinition = "uuid", updatable = false, nullable = false)
    @Schema(description = "ID клиента")
    private UUID clientId;

    @Column(name = "last_name", nullable = false)
    @Schema(description = "Фамилия")
    private String lastName;

    @Column(name = "first_name", nullable = false)
    @Schema(description = "Имя")
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    @Schema(description = "Отчество")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
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
    @JoinColumn(name = "passport_id", nullable = false)
    @Schema(description = "Паспорт клиента")
    private Passport passport;

    @ManyToOne
    @JoinColumn(name = "employment_id")
    @Schema(description = "Информация о занятости")
    private Employment employment;

    @Column(name = "account_number")
    @Schema(description = "Номер счета")
    private String accountNumber;
}