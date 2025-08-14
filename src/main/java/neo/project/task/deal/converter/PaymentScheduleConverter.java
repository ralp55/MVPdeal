package neo.project.task.deal.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.dto.PaymentScheduleElementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Converter(autoApply = false)
public class PaymentScheduleConverter implements AttributeConverter<List<PaymentScheduleElementDto>, String> {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        PaymentScheduleConverter.objectMapper = mapper;
    }

    @Override
    public String convertToDatabaseColumn(List<PaymentScheduleElementDto> attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting paymentSchedule to JSON", e);
        }
    }

    @Override
    public List<PaymentScheduleElementDto> convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null :
                    objectMapper.readValue(dbData, new TypeReference<List<PaymentScheduleElementDto>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON to paymentSchedule", e);
        }
    }
}
