package org.mithwick93.accountable.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntity {

    @Column(name = "created", updatable = false)
    @Generated(event = EventType.INSERT)
    private LocalDateTime created;

    @Column(name = "modified")
    @Generated(event = EventType.UPDATE)
    private LocalDateTime modified;

    @PreUpdate
    public void preUpdate() {
        this.setModified(LocalDateTime.now());
    }

}
