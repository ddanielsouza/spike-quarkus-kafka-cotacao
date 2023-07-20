package souza.oliveira.daniel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import souza.oliveira.daniel.client.CurrencyPriceClient;
import souza.oliveira.daniel.dto.CurrencyPriceDTO;
import souza.oliveira.daniel.dto.QuotationDTO;
import souza.oliveira.daniel.entity.QuotationEntity;
import souza.oliveira.daniel.message.KafkaEvents;
import souza.oliveira.daniel.repositories.QuotationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class QuotationService {
    private final CurrencyPriceClient currencyPriceClient;
    private final QuotationRepository quotationRepository;
    private final KafkaEvents kafkaEvents;

    @Inject
    public QuotationService(
            @RestClient CurrencyPriceClient currencyPriceClient,
            QuotationRepository quotationRepository,
            KafkaEvents kafkaEvents) {
        this.currencyPriceClient = currencyPriceClient;
        this.quotationRepository = quotationRepository;
        this.kafkaEvents = kafkaEvents;
    }

    public void getCurrencyPrice(){
        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient
                .getPriceByPair(CurrencyPriceClient.USD_BRL);

        if(updateCurrentInfoPrice(currencyPriceInfo)){
            final var dto = QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.USDBRL.bid))
                    .date(LocalDateTime.now())
                    .build();

            kafkaEvents.sendNewKafkaEvent(dto);
        }
    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo){
        final var quotationUSDBRL = currencyPriceInfo.USDBRL;
        final var currentPrice = new BigDecimal(quotationUSDBRL.bid);
        boolean updatePrice = false;

        final var lastQuotationOp = quotationRepository.lastQuotation();

        if(lastQuotationOp.isEmpty()){
            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        } else if (currentPrice.compareTo(lastQuotationOp.get().getCurrencyPrice()) > 0) {

            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        }

        return updatePrice;
    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceInfo) {
        final var quotationUSDBRL = currencyPriceInfo.USDBRL;

        final var entity = new QuotationEntity();
        entity.setDate(LocalDateTime.now());
        entity.setCurrencyPrice(new BigDecimal(quotationUSDBRL.bid));
        entity.setPctChange(quotationUSDBRL.pctChange);
        entity.setPair(CurrencyPriceClient.USD_BRL);

        this.quotationRepository.persist(entity);
    }
}
