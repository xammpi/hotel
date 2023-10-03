package org.calisto.hotel.listener;

import org.calisto.hotel.entity.Audit;

public interface Auditable {
    Audit getAudit();

    void setAudit(Audit audit);
}
