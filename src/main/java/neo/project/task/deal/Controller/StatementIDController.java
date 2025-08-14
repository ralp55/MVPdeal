package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.FinishRegistrationRequestDto;
import neo.project.task.deal.service.StatementIDServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deal")
@Tag(name = "Deal API", description = "API для завершения регистрации и расчёта кредита")
@RequiredArgsConstructor
public class StatementIDController {

    private StatementIDServiceInterface dealCalculationService;

    @Operation(
            summary = "Завершение регистрации",
            description = "Выполняет финальный расчет кредита и сохраняет кредит в систему",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Кредит успешно рассчитан и сохранен"),
                    @ApiResponse(responseCode = "404", description = "Заявка не найдена",
                            content = @Content(schema = @Schema(hidden = true))),
            }
    )
    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> finishRegistration(
            @PathVariable UUID statementId,
            @RequestBody FinishRegistrationRequestDto request
    ) {
        log.info("Начало финального расчета по заявке {}", statementId);
        dealCalculationService.finishRegistration(statementId, request);
        log.info("Кредит успешно рассчитан и сохранён для заявки {}", statementId);
        return ResponseEntity.ok().build();
    }
}
