package com.examschedulingproject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.IStudentService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.ICourseRegistrationDao;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.CourseRegistration;
import com.examschedulingproject.entities.concretes.Student;

@Service
public class StudentManager implements IStudentService{
	
	private IStudentDao studentDao;
	private ICourseRegistrationDao courseRegistrationDao;
	
	@Autowired
	public StudentManager(IStudentDao studentDao, ICourseRegistrationDao courseRegistrationDao) {
		super();
		this.studentDao = studentDao;
		this.courseRegistrationDao = courseRegistrationDao;
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

	@Override
	public DataResult<List<Course>> getCoursesByStudent(Long id) {
		Student student = studentDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

        List<CourseRegistration> registrations = courseRegistrationDao.findByStudent(student);
        List<Course> courses = new ArrayList<>();

        for (CourseRegistration registration : registrations) {
            courses.add(registration.getCourse());
        }

        return new SuccessDataResult<List<Course>>
		(courses, "Courses listed.");
	}



}
