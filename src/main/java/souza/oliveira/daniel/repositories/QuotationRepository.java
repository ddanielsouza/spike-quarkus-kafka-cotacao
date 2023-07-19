package souza.oliveira.daniel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import souza.oliveira.daniel.entity.QuotationEntity;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationEntity> {
}
