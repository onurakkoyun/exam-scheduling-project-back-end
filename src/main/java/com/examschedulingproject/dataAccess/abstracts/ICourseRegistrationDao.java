package com.examschedulingproject.dataAccess.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.CourseRegistration;
import com.examschedulingproject.entities.concretes.Student;

public interface ICourseRegistrationDao extends JpaRepository<CourseRegistration, Long>{

	Optional<CourseRegistration> findByStudentIdAndCourseId(Long studentId, Long courseId);

	List<CourseRegistration> findByStudent(Student student);
	
	
	/*@Query("Select new com.examschedulingproject.entities.dtos.StudentExamsWithDetailsDto(co.courseName, ex.examType, ex.examDate, ex.startTime, ex.endTime, ex.description) "
			+ "From CourseRegistration cr join Student st on cr.student.id = cr.id "
			+ "join Course co on cr.course.id = co.id join "
			+ "Exam ex on co.exams.id = ex.id WHERE st.id=:st.id ")
	Optional<StudentExamsWithDetailsDto> getExamsByStudentId(@Param("id") Long id);*/
}
