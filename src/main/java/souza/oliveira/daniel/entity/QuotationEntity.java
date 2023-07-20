package souza.oliveira.daniel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="quotations")
@Data
@NoArgsConstructor
public class QuotationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime date;
    @Column(name="currency_price", precision = 32, scale = 6)
    private BigDecimal currencyPrice;
    @Column(name="pct_change")
    private String pctChange;
    @Column
    private String pair;
}
