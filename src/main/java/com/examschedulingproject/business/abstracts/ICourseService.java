package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.Course;


public interface ICourseService {
	
	Result add(Course course);
	Result delete(Long id);
	DataResult<List<Course>> getAllCourse();

}
