package com.examschedulingproject.dataAccess.abstracts;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.ClassRoom;
import com.examschedulingproject.entities.concretes.Exam;

public interface IExamDao extends JpaRepository<Exam, Long>{

	List<Exam> findByClassRoomAndExamDateAndStartTimeAndEndTime(ClassRoom classRoom, LocalDate examDate,
            String startTime, String endTime);
	
	List<Exam> findByCourseIdIn(List<Long> courseIds);
	

}
