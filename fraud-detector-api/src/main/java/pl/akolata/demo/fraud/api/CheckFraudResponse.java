package pl.akolata.demo.fraud.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckFraudResponse implements Serializable {
    private String rejectionReason;
    private FraudCheckStatus status;

    public enum FraudCheckStatus {
        FRAUD, NOT_FRAUD
    }
}
