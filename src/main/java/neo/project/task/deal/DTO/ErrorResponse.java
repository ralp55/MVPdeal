package neo.project.task.deal.DTO;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
}
