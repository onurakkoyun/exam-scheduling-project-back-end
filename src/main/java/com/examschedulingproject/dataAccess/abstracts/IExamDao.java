package com.examschedulingproject.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.Exam;

public interface IExamDao extends JpaRepository<Exam, Long>{

	List<Exam> findByCourseIdIn(List<Long> courseIds);
	

}
