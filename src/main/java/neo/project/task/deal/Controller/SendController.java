package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.ClientDTO;
import neo.project.task.deal.dto.LoanStatementRequestDto;
import neo.project.task.deal.service.SendService;
import neo.project.task.deal.service.SendServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deal")
@Tag(name = "Statement API", description = "API для выбора кредитного предложения")
@RequiredArgsConstructor
public class SendController {

    private final SendServiceInterface sendService;

    @Operation(
            summary = "Отправка запроса",
            description = "Вычисляет список возможных кредитных предложений по параметрам клиента.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientDTO.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные входные данные",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неавторизованный доступ",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ресурс не найден",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Конфликт данных",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Неподдерживаемый тип данных",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "429",
                            description = "Слишком много запросов",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Сервис временно недоступен",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{statementId}/send")
    public ResponseEntity<Void> sendDocuments(@PathVariable UUID statementId,@Valid @RequestBody List<String> request) {
        log.info("Received request to send documents for postId={}, request={}", statementId, request);
        sendService.sendClientDocuments(statementId, request);
        log.info("Documents sent successfully for postId={}", statementId);
        return ResponseEntity.ok().build();
    }
}



