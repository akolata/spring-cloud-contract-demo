package pl.akolata.demo.fraud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.demo.fraud.api.CheckClientRequest;
import pl.akolata.demo.fraud.api.CheckClientResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ClientController {

    @PutMapping(
            value = "/client-check",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CheckClientResponse> checkClient(@RequestBody CheckClientRequest request) {
        if ("CHROME".equals(request.getBrowser())) {
            return ResponseEntity.ok(new CheckClientResponse(CheckClientResponse.BrowserStatus.OK));
        } else {
            return ResponseEntity.ok(new CheckClientResponse(CheckClientResponse.BrowserStatus.NOT_OK));
        }
    }
}
