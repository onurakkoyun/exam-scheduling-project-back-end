package com.examschedulingproject.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.Admin;
import com.examschedulingproject.entities.concretes.Teacher;

public interface ITeacherDao extends JpaRepository<Teacher, Long>{
	
	Optional<Admin> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	

}
