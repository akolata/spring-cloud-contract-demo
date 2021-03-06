package pl.akolata.demo.loan.api;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.akolata.demo.fraud.api.CheckFraudRequest;
import pl.akolata.demo.fraud.api.CheckFraudResponse;
import pl.akolata.demo.loan.model.TakeLoanRequest;
import pl.akolata.demo.loan.model.TakeLoanResponse;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class LoanController {

    private final RestTemplate restTemplate;

    @Value("${fraud-detector-service.protocol}")
    private String fraudDetectorServiceProtocol;

    @Value("${fraud-detector-service.host}")
    private String fraudDetectorServiceHost;

    @Setter
    @Value("${fraud-detector-service.port}")
    private Integer fraudDetectorServicePort;

    @PostMapping(
            value = "/loans",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TakeLoanResponse> getLoan(@RequestBody TakeLoanRequest request) {
        CheckFraudRequest checkFraudRequest = new CheckFraudRequest();
        checkFraudRequest.setClientId("1234567890");
        checkFraudRequest.setLoanAmount(request.getAmount());
        URI fraudDetectorServiceURI = URI.create(buildFraudDetectorServiceBaseUrl() + "/api/fraud-check");
        RequestEntity<CheckFraudRequest> checkFraudRequestEntity = RequestEntity
                .put(fraudDetectorServiceURI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(checkFraudRequest);
        ResponseEntity<CheckFraudResponse> checkFraudResponseEntity = restTemplate.exchange(checkFraudRequestEntity, CheckFraudResponse.class);
        if (CheckFraudResponse.FraudCheckStatus.FRAUD == Objects.requireNonNull(checkFraudResponseEntity.getBody()).getStatus()) {
            return ResponseEntity.badRequest().body(new TakeLoanResponse("GET LOST"));
        } else {
            return ResponseEntity.ok(new TakeLoanResponse("TAKE MY MONEY"));
        }
    }

    private String buildFraudDetectorServiceBaseUrl() {
        return String.format("%s://%s:%d", fraudDetectorServiceProtocol, fraudDetectorServiceHost, fraudDetectorServicePort);
    }
}
