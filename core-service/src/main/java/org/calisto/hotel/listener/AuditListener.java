package org.calisto.hotel.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.calisto.hotel.entity.Audit;

import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void prePersist(Auditable auditable) {
        Audit audit = auditable.getAudit();
        if (audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }
        audit.setDateRecordCreated(LocalDateTime.now());
        audit.setCreatedBy("Anonymous");
    }

    @PreUpdate
    public void preUpdate(Auditable auditable) {
        Audit audit = auditable.getAudit();
        audit.setDateRecordUpdated(LocalDateTime.now());
        audit.setUpdatedBy("Anonymous");
    }
}
