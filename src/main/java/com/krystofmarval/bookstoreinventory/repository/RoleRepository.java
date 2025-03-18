package com.krystofmarval.bookstoreinventory.repository;

import com.krystofmarval.bookstoreinventory.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
