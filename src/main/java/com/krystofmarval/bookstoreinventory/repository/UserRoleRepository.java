package com.krystofmarval.bookstoreinventory.repository;

import com.krystofmarval.bookstoreinventory.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Custom queries can be defined here as needed.
}
