package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.LoanOfferDto;
import neo.project.task.deal.service.SelectServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/deal")
@Tag(name = "Statement API", description = "API для выбора кредитного предложения")
@RequiredArgsConstructor
public class SelectController {

    private SelectServiceInterface statementService;

    @Operation(
            summary = "Выбор одного из предложений",
            description = "Сохраняет выбранное клиентом кредитное предложение.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Предложение успешно выбрано"),
                    @ApiResponse(responseCode = "404", description = "Заявка не найдена",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/offer/select")
    public ResponseEntity<Void> selectLoanOffer(@Valid @RequestBody LoanOfferDto offer) {
        log.info("Received selected offer: {}", offer);
        statementService.applyLoanOffer(offer);
        log.info("Offer applied successfully for statementId={}", offer.getStatementId());
        return ResponseEntity.ok().build();
    }
}

