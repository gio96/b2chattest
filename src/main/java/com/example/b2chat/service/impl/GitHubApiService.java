package com.example.b2chat.service.impl;

import com.example.b2chat.dto.ExternalApiResponse;
import com.example.b2chat.service.IGithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GitHubApiService implements IGithubService {

    @Value("${github.url}")
    private String apiUrl;

    @Value("${github.access.token}")
    private String accessToken;

    private final RestTemplate restTemplate;

    @Override
    public ExternalApiResponse getOctocatSpoonKnife() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ExternalApiResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                ExternalApiResponse.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        return ExternalApiResponse.builder().build();
    }
}
