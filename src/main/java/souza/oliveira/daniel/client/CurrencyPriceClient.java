package souza.oliveira.daniel.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import souza.oliveira.daniel.dto.CurrencyPriceDTO;

@Path("/last")
@RegisterRestClient
@ApplicationScoped
public interface CurrencyPriceClient {
    public static final String USD_BRL = "USD-BRL";
    @GET
    @Path("/{pair}")
    CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair);
}
