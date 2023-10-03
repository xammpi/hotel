package org.calisto.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Embeddable
@Data
public class Audit {
    @CreatedDate
    @Column(name = "date_record_created")
    private LocalDateTime dateRecordCreated;
    @LastModifiedDate
    @Column(name = "date_record_updated")
    private LocalDateTime dateRecordUpdated;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
