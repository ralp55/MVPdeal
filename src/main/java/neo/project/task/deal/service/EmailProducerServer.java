package neo.project.task.deal.service;

import neo.project.task.deal.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerServer {

    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    @Value("${kafka.topics[0].name}")
    private String finishRegistrationsTopic;

    @Value("${kafka.topics[1].name}")
    private String createdocumentsTopic;

    @Value("${kafka.topics[2].name}")
    private String senddocumentsTopic;

    @Value("${kafka.topics[3].name}")
    private String sendsesTopic;

    @Value("${kafka.topics[4].name}")
    private String creditissuedTopic;

    @Value("${kafka.topics[5].name}")
    private String statementdeniedTopic;

    public void EmailProducer(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToFinishRegistrations(EmailMessage dto) {
        kafkaTemplate.send(finishRegistrationsTopic, dto.getStatementId().toString(), dto);
    }

    public void sendToSendEmails(EmailMessage dto) {
        kafkaTemplate.send(createdocumentsTopic, dto.getStatementId().toString(), dto);
    }

    public void sendToProcessPayments(EmailMessage dto) {
        kafkaTemplate.send(senddocumentsTopic, dto.getStatementId().toString(), dto);
    }
    public void sendTosendsesTopic(EmailMessage dto) {
        kafkaTemplate.send(sendsesTopic, dto.getStatementId().toString(), dto);
    }
    public void sendTocreditissuedTopic(EmailMessage dto) {
        kafkaTemplate.send(creditissuedTopic, dto.getStatementId().toString(), dto);
    }
    public void sendTostatementdeniedTopic(EmailMessage dto) {
        kafkaTemplate.send(statementdeniedTopic, dto.getStatementId().toString(), dto);
    }
}