package com.examschedulingproject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.ICourseService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.ICourseDao;
import com.examschedulingproject.dataAccess.abstracts.ITeacherDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.Teacher;

@Service
public class CourseManager implements ICourseService{
	
	private ICourseDao courseDao;
	private ITeacherDao teacherDao;
	
	@Autowired
	public CourseManager(ICourseDao courseDao, ITeacherDao teacherDao) {
		super();
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
	}

	@Override
	public Result add(Course course) {
		if (courseDao.existsByCourseCode(course.getCourseCode())) {
			return new ErrorResult("Error: Course code is already in use!");
		}
		else {
			
			Long teacherId = course.getTeacher().getId();
		    Teacher teacher = teacherDao.findById(teacherId).orElse(null);
		    course.setTeacher(teacher);
			this.courseDao.save(course);
			return new SuccessResult("Course added.");
		}
		
	}

	@Override
	public Result delete(Long id) {
		if(!courseDao.existsById(id)){
            return new ErrorResult("Course not found!");
        }
		this.courseDao.deleteById(id);
		return new SuccessResult("Course with id "+id+" has been deleted success.");
	}

	@Override
	public DataResult<List<Course>> getAllCourse() {
		return new SuccessDataResult<List<Course>>
		(this.courseDao.findAll(), "Courses listed.");
	}

}
