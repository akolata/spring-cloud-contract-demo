package pl.akolata.demo.fraud.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class FraudController {
    private static final BigDecimal LOAN_FRAUD_THRESHOLD = new BigDecimal("10000");

    @PutMapping(
            value = "/fraud-check",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CheckFraudResponse> checkFraud(@RequestBody CheckFraudRequest request) {
        CheckFraudResponse response = determineIfFraud(request);
        return ResponseEntity.ok(response);
    }

    private CheckFraudResponse determineIfFraud(CheckFraudRequest request) {
        if (request.getLoanAmount().compareTo(LOAN_FRAUD_THRESHOLD) > 0) {
            return new CheckFraudResponse("Amount too high", CheckFraudResponse.FraudCheckStatus.FRAUD);
        } else {
            return new CheckFraudResponse("Amount OK", CheckFraudResponse.FraudCheckStatus.NOT_FRAUD);
        }
    }
}
