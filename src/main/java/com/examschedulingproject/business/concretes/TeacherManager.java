package com.examschedulingproject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.ITeacherService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.ITeacherDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.Exam;
import com.examschedulingproject.entities.concretes.Teacher;
import com.examschedulingproject.exceptions.UserNotFoundException;

@Service
public class TeacherManager implements ITeacherService{
	
	private ITeacherDao teacherDao;
	
	@Autowired
	public TeacherManager(ITeacherDao teacherDao) {
		super();
		this.teacherDao = teacherDao;
	}

	@Override
	public Result add(Teacher teacher) {
		this.teacherDao.save(teacher);
		return new SuccessResult("Teacher added.");
	}

	@Override
	public Result delete(Long id) {
		
		if(!teacherDao.existsById(id)){
            throw new UserNotFoundException(id);
        }
		this.teacherDao.deleteById(id);
		return new SuccessResult("Teacher with id "+id+" has been deleted success.");
	}

	@Override
	public DataResult<List<Teacher>> getAllTeacher() {
		return new SuccessDataResult<List<Teacher>>
		(this.teacherDao.findAll(), "Teacher listed.");
	}

	@Override
	public DataResult<List<Course>> getCoursesByTeacher(Long id) {
		Teacher teacher = teacherDao.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + id));
		return new SuccessDataResult<List<Course>>
		(teacher.getCourses(), "Courses listed.");
	}

	@Override
	public DataResult<List<Exam>> getExamsByTeacherId(Long id) {
		Teacher teacher = teacherDao.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + id));
		List<Course> courses = teacher.getCourses();
        List<Exam> exams = new ArrayList<>();
        for (Course course : courses) {
            exams.addAll(course.getExams());
        }
        return new SuccessDataResult<List<Exam>>
		(exams, "Exams listed.");
	}





}
