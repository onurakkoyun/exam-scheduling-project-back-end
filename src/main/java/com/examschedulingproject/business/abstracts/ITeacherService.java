package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.Exam;
import com.examschedulingproject.entities.concretes.Teacher;

public interface ITeacherService {

	Result add(Teacher teacher);
	Result delete(Long id);
	DataResult<List<Teacher>> getAllTeacher();
	
	DataResult<List<Course>> getCoursesByTeacher(Long id);
	
	DataResult<List<Exam>> getExamsByTeacherId(Long id);
	
}
