package pl.akolata.demo.loan.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = {
                "pl.akolata.demo:fraud-detector-service:+:stubs"
        },
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class LoanServiceContractTest {

    @Autowired
    private LoanService loanService;

    @StubRunnerPort("fraud-detector-service")
    private Integer fraudDetectorStubPort;

    @BeforeEach
    void setup() {
        loanService.setFraudServicePort(fraudDetectorStubPort);
    }

    @Test
    void shouldBeRejectedDueToAbnormalLoanAmount() {
        // when
        boolean canTake = loanService.canTakeLoan("123456789", BigDecimal.valueOf(99999));

        // then
        Assertions.assertFalse(canTake);
    }

}