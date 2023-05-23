package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.CourseRegistration;


public interface ICourseRegistrationService {
	
	Result add(Long studentId, Long courseId);
	Result delete(Long id);
	DataResult<List<CourseRegistration>> getAllCourseRegistration();

	
	/*DataResult<Optional<StudentExamsWithDetailsDto>> getExamsByStudentId(@Param("id") Long id);*/

}
