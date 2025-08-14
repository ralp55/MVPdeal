package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.service.SignService;
import neo.project.task.deal.service.SignServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deal")
@Tag(name = "Sign API", description = "API для отправки документов на подписание")
@RequiredArgsConstructor
public class SignController {

    private final SignServiceInterface signService;

    @Operation(
            summary = "Отправка документов на подписание",
            description = "Отправляет документы на email клиента для электронной подписи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Документы успешно отправлены на подписание"
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
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> sendForSigning(
            @PathVariable UUID dealId,
            @Valid @RequestBody List<String> documentIds) {

        log.info("Отправка документов на подписание для сделки {}: {} документов",
                dealId, documentIds.size());

        signService.sendForSigning(dealId, documentIds);

        log.info("Документы для сделки {} успешно отправлены на подписание", dealId);
        return ResponseEntity.ok().build();
    }
}


