package souza.oliveira.daniel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class QuotationDTO {
    private LocalDateTime date;
    private BigDecimal currencyPrice;
}
