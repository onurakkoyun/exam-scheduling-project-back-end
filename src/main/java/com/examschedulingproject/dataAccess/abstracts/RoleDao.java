package com.examschedulingproject.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examschedulingproject.entities.concretes.ERole;
import com.examschedulingproject.entities.concretes.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
