package com.examschedulingproject.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.examschedulingproject.business.abstracts.IStudentService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorDataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.entities.concretes.CourseRegistration;
import com.examschedulingproject.entities.concretes.Student;
import com.examschedulingproject.exceptions.UserNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
public class StudentsController {
	
	private IStudentService studentService;
	private IStudentDao studentDao;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public StudentsController(IStudentService studentService, IStudentDao studentDao) {
		super();
		this.studentService = studentService;
		this.studentDao = studentDao;
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addStudent(@Valid @RequestBody Student student) {
		return ResponseEntity.ok(this.studentService.add(student));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	 public Result deleteById(@PathVariable("id") Long id) {
	    return this.studentService.delete(id);
	 }
	
	@GetMapping("/getallstudents")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<Student>> getAllStudent(){
		return this.studentService.getAllStudent();
	}
	
	@GetMapping("/getStudentById/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Student getById(@PathVariable Long id) {
		return studentDao.findById(id)
				.orElseThrow();
	}
	
	@GetMapping("/{id}/courseRegistrations")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
	public DataResult<List<CourseRegistration>> getCourseRegistrationsByStudent(@PathVariable Long id){
		return this.studentService.getCourseRegistrationsByStudent(id);		
	}
	
	 
	 @PutMapping("/updateStudentById/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id) {
		 return studentDao.findById(id)
				 .map(student -> {
	                student.setUsername(newStudent.getUsername());
	                student.setEmail(newStudent.getEmail());
	                student.setPassword(encoder.encode(newStudent.getPassword()));
	                student.setFirstName(newStudent.getFirstName());
	                student.setLastName(newStudent.getLastName());
	                student.setFaculty(newStudent.getFaculty());
	                student.setDepartment(newStudent.getDepartment());
	                student.setDegree(newStudent.getDegree());
	                return studentDao.save(student);
	                })
				 .orElseThrow(() -> new UserNotFoundException(id));
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
