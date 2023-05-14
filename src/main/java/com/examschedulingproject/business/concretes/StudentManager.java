package com.examschedulingproject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.IStudentService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.entities.concretes.Student;

@Service
public class StudentManager implements IStudentService{
	
	private IStudentDao studentDao;
	
	@Autowired
	public StudentManager(IStudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	@Override
	public Result add(Student student) {
		this.studentDao.save(student);
		return new SuccessResult("Student added.");
	}

	@Override
	public Result delete(Long id) {
		this.studentDao.deleteById(id);
		return new SuccessResult("Student deleted.");
	}

	@Override
	public DataResult<List<Student>> getAllStudent() {
		return new SuccessDataResult<List<Student>>
		(this.studentDao.findAll(), "Student listed.");
	}


}
