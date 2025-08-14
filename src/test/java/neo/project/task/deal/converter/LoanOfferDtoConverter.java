package neo.project.task.deal.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import neo.project.task.deal.dto.LoanOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoanOfferDtoConverterTest {

    private LoanOfferDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new LoanOfferDtoConverter();
    }

    @Test
    void convertToDatabaseColumn_ShouldReturnJsonString() {
        LoanOfferDto dto = new LoanOfferDto();
        dto.setRequestedAmount(BigDecimal.valueOf(1000));
        dto.setTerm(12);

        String json = converter.convertToDatabaseColumn(dto);
        assertNotNull(json);
        assertTrue(json.contains("\"requestedAmount\":1000"));
    }

    @Test
    void convertToDatabaseColumn_ShouldReturnNull_WhenInputIsNull() {
        String json = converter.convertToDatabaseColumn(null);
        assertNull(json);
    }

    @Test
    void convertToEntityAttribute_ShouldReturnDto() {
        String json = "{\"requestedAmount\":1000,\"term\":12}";
        LoanOfferDto dto = converter.convertToEntityAttribute(json);
        assertNotNull(dto);
        assertEquals(new BigDecimal("1000"), dto.getRequestedAmount());
        assertEquals(12, dto.getTerm());
    }

    @Test
    void convertToEntityAttribute_ShouldReturnNull_WhenInputIsNull() {
        LoanOfferDto dto = converter.convertToEntityAttribute(null);
        assertNull(dto);
    }

    @Test
    void convertToDatabaseColumn_ShouldThrowIllegalArgumentException_OnJsonProcessingException() {

        LoanOfferDtoConverter faultyConverter = new LoanOfferDtoConverter() {
            @Override
            public String convertToDatabaseColumn(LoanOfferDto attribute) {
                throw new IllegalArgumentException("Error converting LoanOfferDto to JSON", new JsonProcessingException("error") {});
            }
        };

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> faultyConverter.convertToDatabaseColumn(new LoanOfferDto()));

        assertTrue(ex.getMessage().contains("Error converting LoanOfferDto to JSON"));
    }

    @Test
    void convertToEntityAttribute_ShouldThrowIllegalArgumentException_OnInvalidJson() {
        String invalidJson = "{invalid json}";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToEntityAttribute(invalidJson));

        assertTrue(ex.getMessage().contains("Error reading JSON to LoanOfferDto"));
    }
}