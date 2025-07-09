package neo.project.task.deal.controller;

import neo.project.task.deal.dto.FinishRegistrationRequestDto;
import neo.project.task.deal.service.StatementIDServiceInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementIDControllerTest {

    @Mock
    private StatementIDServiceInterface dealCalculationService;

    @InjectMocks
    private StatementIDController controller;

    private final UUID statementId = UUID.randomUUID();
    private final FinishRegistrationRequestDto requestDto = new FinishRegistrationRequestDto();

    @Test
    void testFinishRegistrationSuccess() {
        ResponseEntity<Void> response = controller.finishRegistration(statementId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dealCalculationService).finishRegistration(statementId, requestDto);
    }

    @Test
    void testFinishRegistrationThrows() {
        doThrow(new RuntimeException("Statement not found")).when(dealCalculationService)
                .finishRegistration(any(), any());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.finishRegistration(statementId, requestDto)
        );

        assertEquals("Statement not found", exception.getMessage());
    }
}
