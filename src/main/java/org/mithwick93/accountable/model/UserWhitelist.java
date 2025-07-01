package org.mithwick93.accountable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_whitelist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWhitelist {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

}