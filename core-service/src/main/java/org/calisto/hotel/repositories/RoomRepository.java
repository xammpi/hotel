package org.calisto.hotel.repositories;

import org.calisto.hotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAll();

    Page<Room> findAll(Pageable pageable);

    Optional<Room> findByRoomNumber(Integer roomNumber);
}
