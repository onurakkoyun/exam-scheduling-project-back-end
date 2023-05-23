package com.examschedulingproject.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.ICourseRegistrationService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.ICourseDao;
import com.examschedulingproject.dataAccess.abstracts.ICourseRegistrationDao;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.CourseRegistration;
import com.examschedulingproject.entities.concretes.Student;

@Service
public class CourseRegistrationManager implements ICourseRegistrationService{
	
	private ICourseRegistrationDao registrationDao;
	private IStudentDao studentDao;
	private ICourseDao courseDao;
	
	@Autowired
	public CourseRegistrationManager(ICourseRegistrationDao registrationDao, IStudentDao studentDao, ICourseDao courseDao) {
		super();
		this.registrationDao = registrationDao;
		this.studentDao = studentDao;
		this.courseDao = courseDao;
	}


	@Override
	public Result add(Long studentId, Long courseId) {
		
		Student student = studentDao.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı."));
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs bulunamadı."));

        CourseRegistration registerCourse = new CourseRegistration();
        registerCourse.setStudent(student);
        registerCourse.setCourse(course);

        if (!isCoursePresent(studentId, courseId)) {
        	
        	student.getCourseRegistrations().add(registerCourse);

            this.studentDao.save(student);
    		return new SuccessResult("The student has been registered for the course.");
			
		}
        else {
        	return new ErrorResult("The student is already enrolled in the course!");
		}
        
	}

	@Override
	public Result delete(Long id) {
		this.registrationDao.deleteById(id);
		return new SuccessResult("The student's course record has been deleted..");
	}

	@Override
	public DataResult<List<CourseRegistration>> getAllCourseRegistration() {
		return new SuccessDataResult<List<CourseRegistration>>
		(this.registrationDao.findAll(), "Courses listed.");
	}
	
	public boolean isCoursePresent(Long studentId, Long courseId) {
        Optional<CourseRegistration> registerCourse = registrationDao.findByStudentIdAndCourseId(studentId, courseId);
        return registerCourse.isPresent();
    }


}
