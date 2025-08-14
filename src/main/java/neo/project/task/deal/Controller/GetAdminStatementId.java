package neo.project.task.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.service.GetAdminStatementIdService;
import neo.project.task.deal.service.GetAdminStatementIdServiceInterface;
import neo.project.task.deal.service.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
@Tag(name = "Statement API", description = "API для работы с заявками")
public class GetAdminStatementId {

    private final GetAdminStatementIdServiceInterface statementService;

    @Operation(
            summary = "Получить заявку по ID",
            description = "Возвращает полную информацию о заявке по её идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заявка найдена",
                            content = @Content(schema = @Schema(implementation = Statement.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Заявка не найдена",
                            content = @Content
                    )
            }
    )
    @GetMapping("/statement/{statementId}")
    public ResponseEntity<Statement> getStatementById(
            @Parameter(description = "UUID заявки", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID statementId) {

        Statement statement = statementService.getStatementById(statementId);
        return ResponseEntity.ok(statement);
    }
    @GetMapping("/statement")
    public ResponseEntity<List<Statement>> getAllStatements() {
        List<Statement> statements = statementService.getAllStatements();
        return ResponseEntity.ok(statements);
    }
}