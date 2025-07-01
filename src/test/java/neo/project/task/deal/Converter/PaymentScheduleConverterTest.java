package neo.project.task.deal.Converter;

import neo.project.task.deal.DTO.PaymentScheduleElementDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentScheduleConverterTest {

    private PaymentScheduleConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PaymentScheduleConverter();
    }


    @Test
    void convertToDatabaseColumn_ShouldReturnNull_WhenInputIsNull() {
        // Act
        String json = converter.convertToDatabaseColumn(null);

        // Assert
        assertNull(json);
    }



    @Test
    void convertToEntityAttribute_ShouldReturnNull_WhenInputIsNull() {
        // Act
        List<PaymentScheduleElementDto> list = converter.convertToEntityAttribute(null);

        // Assert
        assertNull(list);
    }

    @Test
    void convertToEntityAttribute_ShouldThrowException_WhenInvalidJson() {
        // Arrange
        String invalidJson = "[{invalid json}]";

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToEntityAttribute(invalidJson);
        });

        assertTrue(ex.getMessage().contains("Error reading JSON to paymentSchedule"));
    }
}