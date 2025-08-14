package neo.project.task.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "passport")
@Schema(description = "Паспорт клиента")
public class Passport {

    @Id
    @GeneratedValue
    @Column(name = "pasport_id", columnDefinition = "uuid")
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
