package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    List<RoomDTO> findAll();

    Page<RoomDTO> findAll(Pageable pageable);

    RoomDTO findById(Integer id);

    RoomDTO save(RoomDTO dto);

    RoomDTO update(Integer id, RoomDTO dto);

    void deleteById(Integer id);

    RoomDTO findByRoomNumber(Integer roomNumber);
}
