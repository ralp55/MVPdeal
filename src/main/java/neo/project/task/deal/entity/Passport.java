package neo.project.task.deal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "passport")
@Schema(description = "Паспорт клиента")
public class Passport {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "pasport_id", columnDefinition = "uuid", updatable = false, nullable = false)
    @Schema(description = "ID паспорта")
    private UUID passportId;

    @Column(name = "series", nullable = false, length = 4)
    @Schema(description = "Серия паспорта")
    private String series;

    @Column(name = "number_passport", nullable = false, length = 6)
    @Schema(description = "Номер паспорта")
    private String numberPassport;

    @Column(name = "isuue_branch")
    @Schema(description = "Орган выдавший паспорт")
    private String issueBranch;

    @Column(name = "issue_date")
    @Schema(description = "Дата выдачи паспорта")
    private LocalDate issueDate;
}
