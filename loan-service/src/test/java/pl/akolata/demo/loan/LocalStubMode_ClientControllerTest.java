package pl.akolata.demo.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.akolata.demo.loan.api.ClientController;
import pl.akolata.demo.loan.model.CheckLoanClientRequest;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = {
                "pl.akolata.demo:fraud-detector-service:+:stubs"
        },
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class LocalStubMode_ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClientController clientController;

    @StubRunnerPort("fraud-detector-service")
    private Integer fraudDetectorStubPort;

    @BeforeEach
    void setup() {
        this.clientController.setFraudDetectorServicePort(fraudDetectorStubPort);
    }

    @Test
    @SneakyThrows
    void shouldReturnCoolClient() {
        // given
        CheckLoanClientRequest request = new CheckLoanClientRequest("TEST_CHROME");

        // expect
        this.mvc.perform(MockMvcRequestBuilders.post("/api/clients")
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is("COOL CLIENT")));
    }

    @Test
    @SneakyThrows
    void shouldReturnFraudClient() {
        // given
        CheckLoanClientRequest request = new CheckLoanClientRequest("IE9");

        // expect
        this.mvc.perform(MockMvcRequestBuilders.post("/api/clients")
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is("FRAUD CLIENT")));
    }

}
