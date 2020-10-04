package pl.akolata.demo.fraud.base;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import pl.akolata.demo.fraud.controller.ClientController;

public class ClientsBase {

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new ClientController());
    }
}
