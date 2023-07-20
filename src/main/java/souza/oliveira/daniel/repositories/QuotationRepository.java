package souza.oliveira.daniel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import souza.oliveira.daniel.entity.QuotationEntity;

import java.util.Optional;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationEntity> {
    public Optional<QuotationEntity> lastQuotation(){
        final var jpa = "SELECT q FROM QuotationEntity q WHERE q.currencyPrice IS NOT NULL";

        return this.find(jpa, Sort.descending("date"))
                .firstResultOptional();
    }
}
