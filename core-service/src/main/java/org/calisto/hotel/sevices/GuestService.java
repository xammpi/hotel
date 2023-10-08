package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.GuestDTO;

public interface GuestService extends CrudService<GuestDTO> {

    GuestDTO findByEmail(String email);
}
