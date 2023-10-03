package org.calisto.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.calisto.hotel.listener.AuditListener;
import org.calisto.hotel.listener.Auditable;

@EntityListeners(AuditListener.class)
@MappedSuperclass
@Data
public abstract class BaseEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private boolean expired;
    @Embedded
    private Audit audit;
}
