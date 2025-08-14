package neo.project.task.deal.controller;

import neo.project.task.deal.dto.EmailMessage;
import neo.project.task.deal.service.EmailProducerServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class EmailProducerController {
    private EmailProducerServer producer;

    public void ProducerController(EmailProducerServer producer) {
        this.producer = producer;
    }

    @PostMapping("/finish-registrations")
    public void sendFinishRegistration(@RequestBody EmailMessage dto) {
        producer.sendToFinishRegistrations(dto);
    }

    @PostMapping("/send-emails")
    public void sendEmail(@RequestBody EmailMessage dto) {
        producer.sendToSendEmails(dto);
    }

    @PostMapping("/process-payments")
    public void sendProcessPayment(@RequestBody EmailMessage dto) {
        producer.sendToProcessPayments(dto);
    }

    @PostMapping("/process-payments1")
    public void sendProcessPayment1(@RequestBody EmailMessage dto) {
        producer.sendTosendsesTopic(dto);
    }

    @PostMapping("/process-payments2")
    public void sendProcessPayment2(@RequestBody EmailMessage dto) {
        producer.sendTocreditissuedTopic(dto);
    }

    @PostMapping("/process-payments3")
    public void sendProcessPayment3(@RequestBody EmailMessage dto) {
        producer.sendTostatementdeniedTopic(dto);
    }
}
