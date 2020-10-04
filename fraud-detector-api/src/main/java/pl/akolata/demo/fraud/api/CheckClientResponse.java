package pl.akolata.demo.fraud.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckClientResponse implements Serializable {
    private String browser;
    private BrowserStatus status;
//    @JsonFormat(pattern =  "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    @JsonFormat(pattern =  "yyyy-MM-dd'T'HH:mm:ssXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime checkedAt;

    public enum BrowserStatus {
        OK, NOT_OK
    }
}
