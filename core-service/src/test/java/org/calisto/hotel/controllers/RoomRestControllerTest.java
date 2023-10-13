package org.calisto.hotel.controllers;

import org.calisto.hotel.controllers.impl.RoomRestController;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.sevices.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.calisto.hotel.utils.Utils.generateRandomNumber;

@ExtendWith(MockitoExtension.class)
public class RoomRestControllerTest {
    @Mock
    private RoomServiceImpl roomService;

    @InjectMocks
    private RoomRestController roomController;
    private RoomDTO roomDTO;

    @BeforeEach
    public void setup() {
        var id = generateRandomNumber(1, 1000);
        var roomNumber = generateRandomNumber(1, 777);
        var capacity = generateRandomNumber(1, 7);
        roomDTO = new RoomDTO(id, roomNumber, capacity);
    }
}


