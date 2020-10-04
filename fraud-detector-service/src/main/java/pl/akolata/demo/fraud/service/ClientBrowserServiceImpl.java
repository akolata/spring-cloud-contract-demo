package pl.akolata.demo.fraud.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ClientBrowserServiceImpl implements ClientBrowserService {
    private static final Set<String> OK_BROWSERS = Set.of("CHROME", "FIREFOX");

    @Override
    public Collection<String> getOkBrowsers() {
        return new HashSet<>(OK_BROWSERS);
    }
}
