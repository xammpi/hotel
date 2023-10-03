package org.calisto.hotel.sevices.guest;

import org.calisto.hotel.dto.GuestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuestService {

    List<GuestDTO> findAll();

    Page<GuestDTO> findAll(Pageable pageable);

    GuestDTO findById(Integer id);

    GuestDTO save(GuestDTO dto);

    GuestDTO update(Integer id, GuestDTO dto);

    void deleteById(Integer id);

    GuestDTO findByEmail(String email);
}
