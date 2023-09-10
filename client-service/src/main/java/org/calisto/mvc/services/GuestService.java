package org.calisto.mvc.services;


import org.calisto.mvc.config.CustomPageImpl;
import org.calisto.mvc.models.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GuestService {

    @Value("${hotel.guests.api.url}")
    private String guestBaseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public GuestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Guest> findAll() {
        ResponseEntity<List<Guest>> response = restTemplate.exchange(
                guestBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public Page<Guest> findGuestsUsingPagination(int page) {
        ResponseEntity<CustomPageImpl<Guest>> responseEntity = restTemplate.exchange(
                guestBaseUrl.concat("/search?page={page}"), HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                }, page);
        return responseEntity.getBody();
    }

    public Guest findById(int id) {
        return restTemplate.getForObject(String.format("%s/%d", guestBaseUrl, id), Guest.class);
    }

    public void save(Guest guest) {
        restTemplate.postForObject(guestBaseUrl, guest, Guest.class);
    }

    public void update(int id, Guest updatedGuest) {
        restTemplate.put(String.format("%s/%d", guestBaseUrl, id), updatedGuest, Guest.class);
    }

    public void deleteById(int id) {
        restTemplate.delete(String.format("%s/%d", guestBaseUrl, id));
    }
}
