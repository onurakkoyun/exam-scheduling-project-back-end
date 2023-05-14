package com.examschedulingproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.Course;

public interface ICourseDao extends JpaRepository<Course, Long>{
	
	Boolean existsByCourseCode(String courseCode);

}
