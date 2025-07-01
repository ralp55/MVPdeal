package neo.project.task.deal.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import neo.project.task.deal.DTO.LoanOfferDto;

@Converter(autoApply = false)
public class LoanOfferDtoConverter implements AttributeConverter<LoanOfferDto, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
