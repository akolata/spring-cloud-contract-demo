package pl.akolata.demo.fraud;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import pl.akolata.demo.fraud.api.FraudController;

public class BaseTestClass {

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new FraudController());
    }
}
