package org.calisto.mvc.services;


import org.calisto.mvc.config.CustomPageImpl;
import org.calisto.mvc.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationService {

    @Value("${hotel.reservations.api.url}")
    private String reservationBaseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public ReservationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<Reservation> findReservationUsingPagination(int page) {
        ResponseEntity<CustomPageImpl<Reservation>> responseEntity = restTemplate.exchange(
                reservationBaseUrl.concat("/search?page={page}"), HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                }, page);
        return responseEntity.getBody();
    }

    public Reservation findById(int id) {
        return restTemplate.getForObject(String.format("%s/%d", reservationBaseUrl, id), Reservation.class);
    }

    public void save(Reservation reservation) {
        restTemplate.postForObject(reservationBaseUrl, reservation, Reservation.class);
    }

    public void update(int id, Reservation updatedReservation) {
        restTemplate.put(String.format("%s/%d", reservationBaseUrl, id), updatedReservation, Reservation.class);
    }

    public void deleteById(int id) {
        restTemplate.delete(String.format("%s/%d", reservationBaseUrl, id));
    }
}
