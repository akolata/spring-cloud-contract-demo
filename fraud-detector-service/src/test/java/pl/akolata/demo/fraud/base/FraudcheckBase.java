package pl.akolata.demo.fraud.base;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import pl.akolata.demo.fraud.controller.FraudController;

public class FraudcheckBase {

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new FraudController());
    }
}
