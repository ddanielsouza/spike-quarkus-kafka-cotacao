package souza.oliveira.daniel.schedule;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import souza.oliveira.daniel.service.QuotationService;

@ApplicationScoped
public class QuotationSchedule {
    private final QuotationService quotationService;

    @Inject
    public QuotationSchedule(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @Transactional
    @Scheduled(every = "30m", identity = "task-job")
    public void schedule(){
        this.quotationService.getCurrencyPrice();
    }
}
