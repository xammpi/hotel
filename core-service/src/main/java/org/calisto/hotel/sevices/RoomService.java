package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.RoomDTO;

public interface RoomService extends CrudService<RoomDTO> {

    RoomDTO findByRoomNumber(Integer roomNumber);

    boolean isRoomNumberExist(Integer roomNumber);
}
