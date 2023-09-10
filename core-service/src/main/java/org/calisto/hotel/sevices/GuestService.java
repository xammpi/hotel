package org.calisto.hotel.sevices;

import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.exception.GuestAlreadyExistException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }


    public Page<Guest> getAllGuests(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }


    public Guest findById(Integer id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
    }

    public Guest findByEmail(String email) {
        return guestRepository.findGuestByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "email", email));
    }

    @Modifying
    @Transactional
    public Guest save(Guest guest) {
        if (isGuestExist(guest.getEmail())) {
            throw new GuestAlreadyExistException(
                    String.format("The guest with email: %s is already exist",
                            guest.getEmail())
            );
        }
        return guestRepository.save(guest);
    }

    @Modifying
    @Transactional
    public Guest update(Integer id, Guest updatedGuest) {
        Guest guest = findById(id);
        guest.setReservations(updatedGuest.getReservations());
        guest.setEmail(updatedGuest.getEmail());
        guest.setFirstName(updatedGuest.getFirstName());
        guest.setLastName(updatedGuest.getLastName());
        guest.setPhoneNumber(updatedGuest.getPhoneNumber());
        return guestRepository.save(guest);
    }

    @Modifying
    @Transactional
    public void deleteById(Integer id) {
        guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
        guestRepository.deleteById(id);
    }

    private boolean isGuestExist(String email) {
        Optional<Guest> existingRoom = guestRepository.findGuestByEmail(email);
        return existingRoom.isPresent();
    }
}
