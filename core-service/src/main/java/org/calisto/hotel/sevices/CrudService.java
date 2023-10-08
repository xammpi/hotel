package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<D extends BaseDTO> {
    List<D> findAll();

    Page<D> findAll(Pageable pageable);

    D findById(Integer id);

    D save(D e);

    D update(Integer id, D e);

    void deleteById(Integer id);
}
