package org.calisto.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static org.calisto.hotel.util.constants.NameConstants.ANONYMOUS;
import static org.calisto.hotel.util.constants.NameConstants.UPDATED_ANONYMOUS;

@MappedSuperclass
@Getter
@Setter
public abstract class Audit {
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
        this.dateRecordCreated = LocalDateTime.now();
        this.createdBy = ANONYMOUS;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateRecordUpdated = LocalDateTime.now();
        this.updatedBy = UPDATED_ANONYMOUS;
    }
}
