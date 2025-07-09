package neo.project.task.deal.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.dto.StatementStatusHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Converter(autoApply = false)
public class StatementStatusHistoryConverter implements AttributeConverter<List<StatementStatusHistoryDto>, String> {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        StatementStatusHistoryConverter.objectMapper = mapper;
    }

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


