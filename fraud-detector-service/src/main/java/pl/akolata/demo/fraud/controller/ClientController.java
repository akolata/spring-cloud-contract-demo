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
import pl.akolata.demo.fraud.service.ClientBrowserService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ClientController {
    private final ClientBrowserService clientBrowserService;

    @PutMapping(
            value = "/client-check",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CheckClientResponse> checkClient(@RequestBody CheckClientRequest request) {
        Collection<String> okBrowsers = clientBrowserService.getOkBrowsers();
        if (okBrowsers.contains(request.getBrowser())) {
            return ResponseEntity.ok(new CheckClientResponse(request.getBrowser(), CheckClientResponse.BrowserStatus.OK));
        } else {
            return ResponseEntity.ok(new CheckClientResponse(request.getBrowser(), CheckClientResponse.BrowserStatus.NOT_OK));
        }
    }
}
