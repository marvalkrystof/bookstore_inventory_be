package com.krystofmarval.bookstoreinventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userroles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
