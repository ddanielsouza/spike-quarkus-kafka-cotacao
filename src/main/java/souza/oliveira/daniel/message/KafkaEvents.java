package souza.oliveira.daniel.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import souza.oliveira.daniel.dto.QuotationDTO;

@ApplicationScoped
public class KafkaEvents {
    private static Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Enviando cotação para o tópico do kafka --");
        this.quotationRequestEmitter.send(quotation).toCompletableFuture().join();
    }
}
