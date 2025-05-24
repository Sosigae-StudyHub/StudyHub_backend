package com.studyhub.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.studyhub.config.PortOneProperties;
import com.studyhub.service.PortOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PortOneServiceImpl implements PortOneService {

    private final PortOneProperties portOneProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verifyPaymentByPaymentId(String paymentId, int expectedAmount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "PortOne " + portOneProperties.getApiSecret());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "https://api.portone.io/payments/" + paymentId,
                    HttpMethod.GET,
                    entity,
                    JsonNode.class
            );

            JsonNode body = response.getBody();
            int actualAmount = body.get("amount").get("total").asInt();
            String status = body.get("status").asText();

            return actualAmount == expectedAmount && status.equals("PAID");
        } catch (HttpClientErrorException e) {
            // ✅ 여기에 예외 로그 출력 추가
            System.out.println("⛔ PortOne API 오류 응답: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}