package pl.akolata.demo.loan.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.akolata.demo.fraud.api.CheckFraudRequest;
import pl.akolata.demo.fraud.api.CheckFraudResponse;

import java.math.BigDecimal;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class LoanService {
    private final RestTemplate restTemplate;

    @Setter
    private Integer fraudServicePort;

    boolean canTakeLoan(String clientId, BigDecimal amount) {
        CheckFraudRequest request = new CheckFraudRequest(clientId, amount);
        URI fraudDetectorServiceURI = URI.create("http://localhost:" + fraudServicePort + "/api/fraud-check");
        RequestEntity<CheckFraudRequest> checkFraudRequestEntity = RequestEntity
                .put(fraudDetectorServiceURI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request);
        ResponseEntity<CheckFraudResponse> checkFraudResponseEntity = restTemplate
                .exchange(checkFraudRequestEntity, CheckFraudResponse.class);
        return CheckFraudResponse.FraudCheckStatus.FRAUD != checkFraudResponseEntity.getBody().getStatus();
    }
}
