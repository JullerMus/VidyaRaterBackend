package org.example.vidyaraterbackend.Authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TwitchAuthService {

    @Value("${twitch.client-id}")
    private String clientId;

    @Value("${twitch.client-secret}")
    private String clientSecret;

    @Value("${twitch.redirect-uri}")
    private String redirectUri;

    @Value("${twitch.auth-url}")
    private String authUrl;

    @Value("${twitch.api-url}")
    private String apiUrl;


    private WebClient webClient = WebClient.create(authUrl);

    public String exchangeCodeForToken(String code) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/token")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("redirect_uri", redirectUri)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

