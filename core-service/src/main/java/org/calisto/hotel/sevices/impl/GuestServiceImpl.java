package org.calisto.hotel.sevices.impl;

import jakarta.transaction.Transactional;
import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.exception.GuestAlreadyExistException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.GuestRepository;
import org.calisto.hotel.sevices.GuestService;
import org.calisto.hotel.util.converters.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final BaseConverter<GuestDTO, Guest> converter;
    private final GuestRepository guestRepository;


    @Autowired
    public GuestServiceImpl(BaseConverter<GuestDTO, Guest> converter, GuestRepository guestRepository) {
        this.converter = converter;
        this.guestRepository = guestRepository;
    }

    @Override
    public List<GuestDTO> findAll() {
        List<Guest> guestList = guestRepository.findAll();
        return converter.convertToDTOList(guestList);
    }

    @Override
    public Page<GuestDTO> findAll(Pageable pageable) {
        return guestRepository.findAll(pageable)
                .map(converter::convertToDTO);
    }

    @Override
    public GuestDTO findById(Integer id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
        return converter.convertToDTO(guest);
    }


    @Transactional
    @Override
    public GuestDTO save(GuestDTO guestDTO) {
        if (isGuestExist(guestDTO.getEmail())) {
            throw new GuestAlreadyExistException(
                    String.format("The guest with email: %s is already exist",
                            guestDTO.getEmail())
            );
        }
        Guest entity = converter.convertToEntity(guestDTO);
        Guest savedGuest = guestRepository.save(entity);
        return converter.convertToDTO(savedGuest);
    }

    @Transactional
    @Override
    public GuestDTO update(Integer id, GuestDTO updatedGuestDTO) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
        guest.setEmail(updatedGuestDTO.getEmail());
        guest.setFirstName(updatedGuestDTO.getFirstName());
        guest.setLastName(updatedGuestDTO.getLastName());
        guest.setPhoneNumber(updatedGuestDTO.getPhoneNumber());
        Guest savedGuest = guestRepository.save(guest);
        return converter.convertToDTO(savedGuest);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
        guestRepository.deleteById(id);
    }

    @Override
    public GuestDTO findByEmail(String email) {
        Guest guest = guestRepository.findGuestByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "email", email));
        return converter.convertToDTO(guest);
    }

    private boolean isGuestExist(String email) {
        Optional<Guest> existingRoom = guestRepository.findGuestByEmail(email);
        return existingRoom.isPresent();
    }
}
