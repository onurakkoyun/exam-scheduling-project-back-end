package com.examschedulingproject.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.examschedulingproject.business.abstracts.IExamService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorDataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.dataAccess.abstracts.ICourseDao;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.entities.concretes.Course;
import com.examschedulingproject.entities.concretes.Exam;
import com.examschedulingproject.entities.concretes.Student;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exams")
public class ExamsController {
	
	private IExamService examService;
	private ICourseDao courseDao;
	private IStudentDao studentDao;

	@Autowired
	public ExamsController(IExamService examService, IStudentDao studentDao, ICourseDao courseDao) {
		super();
		this.examService = examService;
		this.courseDao = courseDao;
		this.studentDao = studentDao;
	}
	
	@PostMapping("/add/{courseId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")	
    public ResponseEntity<?> addExamToCourse(@PathVariable Long courseId, @RequestBody Exam exam) {
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        exam.setCourse(course);
        return ResponseEntity.ok(this.examService.add(exam));
    }

	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Result deleteById(@PathVariable("id") Long id) {
	    return this.examService.delete(id);
	 }
	
	@GetMapping("/getallexams")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<Exam>> getAllExam(){
		return this.examService.getAllExam();
	}
	
	@GetMapping("/student/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public DataResult<List<Exam>> getExamsByStudent(@PathVariable("id") Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow();
        
        return examService.getExamsByStudent(student);
    }
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
	public DataResult<Exam> updateExamById(@RequestBody Exam newExam, @PathVariable Long id) {
		return examService.updateExamById(newExam, id);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String, String> validationErrors = new HashMap<String, String>();
		
		for (FieldError fieldError: exceptions.getBindingResult().getFieldErrors() ) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama Hataları");
		return errors;
	}

}
