package org.mithwick93.accountable.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntity {
    @Column(name = "created", insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "modified", insertable = false, updatable = false)
    private LocalDateTime modified;
}
