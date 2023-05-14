package com.examschedulingproject.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.examschedulingproject.business.abstracts.ICourseService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorDataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.dataAccess.abstracts.ICourseDao;
import com.examschedulingproject.entities.concretes.Course;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class CoursesController {
	
	private ICourseService courseService;
	private ICourseDao courseDao;

	@Autowired
	public CoursesController(ICourseService courseService, ICourseDao courseDao) {
		super();
		this.courseService = courseService;
		this.courseDao = courseDao;
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addCourse(@Valid @RequestBody Course course) {
		return ResponseEntity.ok(this.courseService.add(course));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	 public Result deleteById(@PathVariable("id") Long id) {
	    return this.courseService.delete(id);
	 }
	
	@GetMapping("/getallcourses")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<Course>> getAllJobPosting(){
		return this.courseService.getAllCourse();
	}
	
	@GetMapping("/getCourseById/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Course getById(@PathVariable Long id) {
		return courseDao.findById(id)
				.orElseThrow();
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
