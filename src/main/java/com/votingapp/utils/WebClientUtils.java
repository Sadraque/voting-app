package com.votingapp.utils;

import com.votingapp.exception.WebClientRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class WebClientUtils {

    private WebClient.Builder getWebClient() {
        return WebClient.builder();
    }

    public <T> ResponseEntity<T> getWithBasicAuth(String baseUrl, URI uri, String username, String password, Class<T> returnType) throws WebClientRequestException {
        ResponseEntity<T> response;

        try {
            response = getWebClient()
                    .defaultHeaders(header -> header.setBasicAuth(username, password))
                    .baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri(uri.toString())
                    .retrieve()
                    .toEntity(returnType)
                    .block();

        } catch (Exception exception) {
            throw new WebClientRequestException(exception);
        }

        return response;
    }

    public <T> ResponseEntity<T> post(String baseUrl, URI uri, Class<T> returnType, Object body) throws WebClientRequestException {
        ResponseEntity<T> response;

        try {
            response = getWebClient()
                    .baseUrl(baseUrl)
                    .build()
                    .post()
                    .uri(uri)
                    .body(Mono.just(body), body.getClass())
                    .retrieve()
                    .toEntity(returnType)
                    .block();

        } catch (Exception exception) {
            throw new WebClientRequestException(exception);
        }

        return response;
    }

    public <T> ResponseEntity<T> put(String baseUrl, URI uri, Class<T> returnType, Object body) throws WebClientRequestException {
        ResponseEntity<T> response;

        try {
            response = getWebClient()
                    .baseUrl(baseUrl)
                    .build()
                    .put()
                    .uri(uri)
                    .body(Mono.just(body), body.getClass())
                    .retrieve()
                    .toEntity(returnType)
                    .block();

        } catch (Exception exception) {
            throw new WebClientRequestException(exception);
        }

        return response;
    }

    public <T> ResponseEntity<T> delete(String baseUrl, URI uri, Class<T> returnType) throws WebClientRequestException {
        ResponseEntity<T> response;

        try {
            response = getWebClient()
                    .baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri(uri.toString())
                    .retrieve()
                    .toEntity(returnType)
                    .block();

        } catch (Exception exception) {
            throw new WebClientRequestException(exception);
        }

        return response;
    }

}
