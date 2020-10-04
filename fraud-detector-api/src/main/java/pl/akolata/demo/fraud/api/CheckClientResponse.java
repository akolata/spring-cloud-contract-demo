package pl.akolata.demo.fraud.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckClientResponse implements Serializable {
    private String browser;
    private BrowserStatus status;

    public enum BrowserStatus {
        OK, NOT_OK
    }
}
