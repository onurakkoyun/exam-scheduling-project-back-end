package com.examschedulingproject.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.CourseRegistration;

public interface ICourseRegistrationDao extends JpaRepository<CourseRegistration, Long>{

	public Optional<CourseRegistration> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
