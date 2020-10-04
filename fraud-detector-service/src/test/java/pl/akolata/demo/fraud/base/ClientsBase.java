package pl.akolata.demo.fraud.base;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import pl.akolata.demo.fraud.controller.ClientController;
import pl.akolata.demo.fraud.service.ClientBrowserService;

import java.util.Collection;
import java.util.Set;

public class ClientsBase {

    @BeforeEach
    void setup() {
        ClientBrowserService clientBrowserService = new ClientBrowserService() {
            @Override
            public Collection<String> getOkBrowsers() {
                return Set.of("TEST_CHROME");
            }
        };
        RestAssuredMockMvc.standaloneSetup(new ClientController(clientBrowserService));
    }
}
