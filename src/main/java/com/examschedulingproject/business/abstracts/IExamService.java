package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.Exam;
import com.examschedulingproject.entities.concretes.Student;

public interface IExamService {
	Result add(Exam exam);
	Result delete(Long id);
	DataResult<List<Exam>> getAllExam();
	
	Exam updateExam(Exam newExam, Long id);
	
	DataResult<List<Exam>> getExamsByStudent(Student student);

}
