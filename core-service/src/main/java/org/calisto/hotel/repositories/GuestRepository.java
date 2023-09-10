package org.calisto.hotel.repositories;

import org.calisto.hotel.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Page<Guest> findAll(Pageable pageable);

    Optional<Guest> findGuestByEmail(String email);
}
