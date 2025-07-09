package neo.project.task.deal.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.dto.LoanOfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = false)
public class LoanOfferDtoConverter implements AttributeConverter<LoanOfferDto, String> {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        LoanOfferDtoConverter.objectMapper = mapper;
    }

    @Override
    public String convertToDatabaseColumn(LoanOfferDto attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting LoanOfferDto to JSON", e);
        }
    }

    @Override
    public LoanOfferDto convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : objectMapper.readValue(dbData, LoanOfferDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON to LoanOfferDto", e);
        }
    }
}
