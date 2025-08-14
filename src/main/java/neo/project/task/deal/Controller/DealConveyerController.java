package neo.project.task.deal.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.project.task.deal.dto.FullProcessConveyerDto;
import neo.project.task.deal.service.DealConveyerServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealConveyerController {
    private final DealConveyerServiceInterface gatewayService;

    @PostMapping("/fulldocument")
    public ResponseEntity<Void> process(@RequestBody FullProcessConveyerDto request) {
        log.info("Starting full process for statement {}", request.getStatementId());
        gatewayService.executeFullProcess(request);
        log.info("Full process completed for statement {}", request.getStatementId());
        return ResponseEntity.ok().build();
    }
}


