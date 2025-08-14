package neo.project.task.deal.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import neo.project.task.deal.dto.StatementStatusHistoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementStatusHistoryConverterTest {

    private StatementStatusHistoryConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StatementStatusHistoryConverter();
    }



    @Test
    void convertToDatabaseColumn_ShouldReturnNull_WhenInputIsNull() {
        String json = converter.convertToDatabaseColumn(null);
        assertNull(json);
    }


    @Test
    void convertToEntityAttribute_ShouldReturnNull_WhenInputIsNull() {
        List<StatementStatusHistoryDto> list = converter.convertToEntityAttribute(null);
        assertNull(list);
    }

    @Test
    void convertToDatabaseColumn_ShouldThrowIllegalArgumentException_OnJsonProcessingException() {
        StatementStatusHistoryConverter faultyConverter = new StatementStatusHistoryConverter() {
            @Override
            public String convertToDatabaseColumn(List<StatementStatusHistoryDto> attribute) {
                throw new IllegalArgumentException("Error converting statusHistory to JSON", new JsonProcessingException("error") {});
            }
        };

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> faultyConverter.convertToDatabaseColumn(new ArrayList<>()));

        assertTrue(ex.getMessage().contains("Error converting statusHistory to JSON"));
    }

    @Test
    void convertToEntityAttribute_ShouldThrowIllegalArgumentException_OnInvalidJson() {
        String invalidJson = "{invalid json}";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToEntityAttribute(invalidJson));

        assertTrue(ex.getMessage().contains("Error reading JSON to statusHistory"));
    }
}