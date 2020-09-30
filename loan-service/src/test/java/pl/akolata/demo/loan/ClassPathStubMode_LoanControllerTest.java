package pl.akolata.demo.loan;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.akolata.demo.loan.api.LoanController;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = {
                "pl.akolata.demo:fraud-detector-service:+:stubs"
        },
        stubsMode = StubRunnerProperties.StubsMode.CLASSPATH
)
public class ClassPathStubMode_LoanControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LoanController loanController;

    @StubRunnerPort("fraud-detector-service")
    private Integer fraudDetectorStubPort;

    @BeforeEach
    void setup() {
        this.loanController.setFraudDetectorServicePort(fraudDetectorStubPort);
    }

    @Test
    @SneakyThrows
    void shouldBeRejectedDueToAbnormalLoanAmount() {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
