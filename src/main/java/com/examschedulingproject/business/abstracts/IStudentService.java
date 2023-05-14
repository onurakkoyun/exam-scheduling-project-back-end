package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.Student;

public interface IStudentService {
	Result add(Student student);
	Result delete(Long id);
	DataResult<List<Student>> getAllStudent();
	
}
