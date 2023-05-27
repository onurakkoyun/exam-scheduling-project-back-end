package com.examschedulingproject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.IExamService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.IExamDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.CourseRegistration;
import com.examschedulingproject.entities.concretes.Exam;
import com.examschedulingproject.entities.concretes.Student;
import com.examschedulingproject.exceptions.UserNotFoundException;
@Service
public class ExamManager implements IExamService{
	
	private IExamDao examDao;
	
	@Autowired
	public ExamManager(IExamDao examDao) {
		super();
		this.examDao = examDao;
	}

	@Override
	public Result add(Exam exam) {
		this.examDao.save(exam);
		return new SuccessResult("Exam added.");
	}

	@Override
	public Result delete(Long id) {
		if(!examDao.existsById(id)){
            return new ErrorResult("Exam not found!");
        }
		this.examDao.deleteById(id);
		return new SuccessResult("Exam with id "+id+" has been deleted success.");
	}

	@Override
	public DataResult<List<Exam>> getAllExam() {
		return new SuccessDataResult<List<Exam>>
		(this.examDao.findAll(), "Exams listed.");
	}
	
	@Override
	public DataResult<List<Exam>> getExamsByStudent(Student student) {
        List<CourseRegistration> registrations = student.getCourseRegistrations();
        List<Long> courseIds = registrations.stream().map(CourseRegistration::getCourse)
                .map(Course::getId).collect(Collectors.toList());
        
        return new SuccessDataResult<List<Exam>>
        (this.examDao.findByCourseIdIn(courseIds),"Exams listed.");
    }

	@Override
	public DataResult<Exam> updateExamById(Exam newExam, Long id) {
		return new SuccessDataResult<Exam>
		(this.examDao.findById(id)
				 .map(exam -> {
					 exam.setExamDate(newExam.getExamDate());
					 exam.setStartTime(newExam.getStartTime());
					 exam.setEndTime(newExam.getEndTime());
					 exam.setExamType(newExam.getExamType());
					 exam.setDescription(newExam.getDescription());
	                return examDao.save(exam);
	                })
				 .orElseThrow(() -> new UserNotFoundException(id)), "Exam updated.");
		 
	}


}
