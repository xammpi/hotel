package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.GuestDTO;

public interface GuestService extends CrudService<GuestDTO, Integer> {
    GuestDTO findByEmail(String email);
}
