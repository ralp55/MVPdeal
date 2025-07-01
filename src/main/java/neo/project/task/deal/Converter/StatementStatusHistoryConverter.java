package neo.project.task.deal.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.DTO.LoanOfferDto;
import neo.project.task.deal.DTO.StatementStatusHistoryDto;

import java.util.List;

@Converter(autoApply = false)
public class StatementStatusHistoryConverter implements AttributeConverter<List<StatementStatusHistoryDto>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<StatementStatusHistoryDto> attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting statusHistory to JSON", e);
        }
    }

    @Override
    public List<StatementStatusHistoryDto> convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON to statusHistory", e);
        }
    }
}


