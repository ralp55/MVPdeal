package neo.project.task.deal.Converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.DTO.PaymentScheduleElementDto;

import java.util.List;

@Converter(autoApply = false)
public class PaymentScheduleConverter implements AttributeConverter<List<PaymentScheduleElementDto>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
