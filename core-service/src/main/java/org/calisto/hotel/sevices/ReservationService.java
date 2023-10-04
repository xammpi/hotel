package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> findAll();

    Page<ReservationDTO> findAll(Pageable pageable);

    ReservationDTO findById(Integer id);

    ReservationDTO save(ReservationDTO dto);

    ReservationDTO update(Integer id, ReservationDTO dto);

    void deleteById(Integer id);

}
