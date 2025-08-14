package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.service.CodeServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
public class CodeController {
    private final CodeServiceInterface codeService;

    @Operation(
            summary = "Подтверждение документов",
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

    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> confirm(
            @PathVariable UUID statementId,
            @RequestParam String code) {

        log.info("Confirming statement {} with code {}", statementId, code);
        codeService.confirmStatement(statementId, code);
        return ResponseEntity.ok().build();
    }
}



