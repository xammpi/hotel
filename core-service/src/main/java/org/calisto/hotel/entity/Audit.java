package org.calisto.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Audit {
    private static final String ANONYMOUS = "Anonymous";
    private static final String UPDATED_ANONYMOUS = "Updated-Anonymous";
    @Column(name = "date_record_created")
    private LocalDateTime dateRecordCreated;
    @Column(name = "date_record_updated")
    private LocalDateTime dateRecordUpdated;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.dateRecordCreated = now;
        this.dateRecordUpdated = now;
        this.createdBy = ANONYMOUS;
        this.updatedBy = ANONYMOUS;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateRecordUpdated = LocalDateTime.now();
        this.updatedBy = UPDATED_ANONYMOUS;
    }
}
