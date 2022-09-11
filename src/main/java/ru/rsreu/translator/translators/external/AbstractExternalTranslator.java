package ru.rsreu.translator.translators.external;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.translator.translators.Translator;

import javax.annotation.PostConstruct;

public abstract class AbstractExternalTranslator implements Translator {
    private final String apiUrl;
    private final String apiVersion;
    private final String apiMethod;

    protected String fullApiPath;
    protected final HttpMethod apiRequestMethod;
    protected final RestTemplate restTemplate;

    public AbstractExternalTranslator(
            String apiUrl,
            String apiVersion,
            String apiMethod,
            HttpMethod apiRequestMethod, RestTemplate restTemplate
    ) {
        this.apiUrl = apiUrl;
        this.apiVersion = apiVersion;
        this.apiMethod = apiMethod;
        this.apiRequestMethod = apiRequestMethod;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        fullApiPath = formFullApiPath(apiUrl, apiVersion, apiMethod);
    }

    private String formFullApiPath(String apiUrl, String apiVersion, String apiMethod) {
        StringBuilder result = new StringBuilder();
        result.append(apiUrl);
        if (!apiUrl.endsWith("/")) {
            result.append("/");
        }
        result.append("v").append(apiVersion);
        if (!apiMethod.startsWith("/")) {
            result.append("/");
        }
        result.append(apiMethod);
        return result.toString();
    }
}
