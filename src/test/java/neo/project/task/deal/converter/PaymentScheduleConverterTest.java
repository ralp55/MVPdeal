package neo.project.task.deal.converter;

import neo.project.task.deal.dto.PaymentScheduleElementDto;
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
        String json = converter.convertToDatabaseColumn(null);
        assertNull(json);
    }



    @Test
    void convertToEntityAttribute_ShouldReturnNull_WhenInputIsNull() {
        List<PaymentScheduleElementDto> list = converter.convertToEntityAttribute(null);
        assertNull(list);
    }

    @Test
    void convertToEntityAttribute_ShouldThrowException_WhenInvalidJson() {
        String invalidJson = "[{invalid json}]";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToEntityAttribute(invalidJson);
        });

        assertTrue(ex.getMessage().contains("Error reading JSON to paymentSchedule"));
    }
}