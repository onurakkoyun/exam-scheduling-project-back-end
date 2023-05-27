package com.examschedulingproject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.Teacher;

public interface ICourseDao extends JpaRepository<Course, Long>{
	
	Boolean existsByCourseCode(String courseCode);

	List<Course> findByTeacher(Teacher teacher);

}
