package org.calisto.hotel.sevices.impl;

import jakarta.transaction.Transactional;
import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.GuestRepository;
import org.calisto.hotel.sevices.GuestService;
import org.calisto.hotel.utils.converters.GuestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestConverter converter;
    private final GuestRepository guestRepository;


    @Autowired
    public GuestServiceImpl(GuestConverter converter,
                            GuestRepository guestRepository) {
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
                .orElseThrow(ResourceNotFoundException::new);
        return converter.convertToDTO(guest);
    }


    @Transactional
    @Override
    public GuestDTO save(GuestDTO guestDTO) {
        if (isGuestExist(guestDTO.getEmail()))
            throw new ResourceConflictException();
        Guest entity = converter.convertToEntity(guestDTO);
        Guest savedGuest = guestRepository.save(entity);
        return converter.convertToDTO(savedGuest);
    }

    @Transactional
    @Override
    public GuestDTO update(Integer id, GuestDTO updatedGuestDTO) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
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
                .orElseThrow(ResourceNotFoundException::new);
        guestRepository.deleteById(id);
    }

    @Override
    public GuestDTO findByEmail(String email) {
        Guest guest = guestRepository.findGuestByEmail(email)
                .orElseThrow(ResourceNotFoundException::new);
        return converter.convertToDTO(guest);
    }

    private boolean isGuestExist(String email) {
        return guestRepository.findGuestByEmail(email)
                .isPresent();
    }
}
