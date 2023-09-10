package org.calisto.mvc.services;


import org.calisto.mvc.config.CustomPageImpl;
import org.calisto.mvc.models.Room;
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
public class RoomService {

    @Value("${hotel.rooms.api.url}")
    private String roomBaseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public RoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Room> findAll() {
        ResponseEntity<List<Room>> response = restTemplate.exchange(
                roomBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public Page<Room> findRoomsUsingPagination(int page) {
        ResponseEntity<CustomPageImpl<Room>> responseEntity = restTemplate.exchange(
                roomBaseUrl.concat("/search?page={page}"), HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                }, page);
        return responseEntity.getBody();
    }

    public Room findById(int id) {
        return restTemplate.getForObject(String.format("%s/%d", roomBaseUrl, id), Room.class);
    }

    public void save(Room room) {
        restTemplate.postForObject(roomBaseUrl, room, Room.class);
    }

    public void update(int id, Room updatedRoom) {
        restTemplate.put(String.format("%s/%d", roomBaseUrl, id), updatedRoom, Room.class);
    }

    public void deleteById(int id) {
        restTemplate.delete(String.format("%s/%d", roomBaseUrl, id));
    }
}
