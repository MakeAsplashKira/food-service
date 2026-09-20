package com.example.foodservice.orderservice.storenotifier;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class StoreNotifier implements StoreNotifierInterface {

    @Override
    public void notifyStore(OrderPaid event) {
        RestClient client = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        client.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(event)
                .retrieve()
                .toBodilessEntity();

    }
}
