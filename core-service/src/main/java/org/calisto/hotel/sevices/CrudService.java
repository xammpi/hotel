package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.GuestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T findById(ID id);

    T save(T dto);

    T update(ID id, T dto);

    void deleteById(ID id);
}
