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
import pl.akolata.demo.fraud.api.CheckClientRequest;
import pl.akolata.demo.fraud.api.CheckClientResponse;
import pl.akolata.demo.loan.model.CheckLoanClientRequest;
import pl.akolata.demo.loan.model.CheckLoanClientResponse;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ClientController {

    private final RestTemplate restTemplate;

    @Value("${fraud-detector-service.protocol}")
    private String fraudDetectorServiceProtocol;

    @Value("${fraud-detector-service.host}")
    private String fraudDetectorServiceHost;

    @Setter
    @Value("${fraud-detector-service.port}")
    private Integer fraudDetectorServicePort;

    @PostMapping(
            value = "/clients",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CheckLoanClientResponse> getLoan(@RequestBody CheckLoanClientRequest request) {
        CheckClientRequest checkClientRequest = new CheckClientRequest();
        checkClientRequest.setBrowser(request.getBrowser());
        URI fraudDetectorServiceURI = URI.create(buildFraudDetectorServiceBaseUrl() + "/api/client-check");
        RequestEntity<CheckClientRequest> checkFraudRequestEntity = RequestEntity
                .put(fraudDetectorServiceURI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(checkClientRequest);
        ResponseEntity<CheckClientResponse> checkFraudResponseEntity = restTemplate.exchange(checkFraudRequestEntity, CheckClientResponse.class);
        if (CheckClientResponse.BrowserStatus.OK == Objects.requireNonNull(checkFraudResponseEntity.getBody()).getStatus()) {
            return ResponseEntity.ok(new CheckLoanClientResponse("COOL CLIENT"));
        } else {
            return ResponseEntity.ok(new CheckLoanClientResponse("FRAUD CLIENT"));
        }
    }

    private String buildFraudDetectorServiceBaseUrl() {
        return String.format("%s://%s:%d", fraudDetectorServiceProtocol, fraudDetectorServiceHost, fraudDetectorServicePort);
    }
}
