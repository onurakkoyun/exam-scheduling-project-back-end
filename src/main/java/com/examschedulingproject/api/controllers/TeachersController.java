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

import com.examschedulingproject.business.abstracts.ITeacherService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorDataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.dataAccess.abstracts.ITeacherDao;
import com.examschedulingproject.entities.concretes.Teacher;
import com.examschedulingproject.exceptions.UserNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teachers")
public class TeachersController {
	
	private ITeacherService teacherService;
	private ITeacherDao teacherDao;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public TeachersController(ITeacherService teacherService, ITeacherDao teacherDao) {
		super();
		this.teacherService = teacherService;
		this.teacherDao = teacherDao;
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTeacher(@Valid @RequestBody Teacher teacher) {
		return ResponseEntity.ok(this.teacherService.add(teacher));
	}
	
	 @GetMapping("/getTeacherById/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Teacher getById(@PathVariable Long id) {
		 return teacherDao.findById(id)
				 .orElseThrow();
		 }
	 
	 @PutMapping("/updateTeacherById/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Teacher updateTeacher(@RequestBody Teacher newTeacher, @PathVariable Long id) {
	        return teacherDao.findById(id)
	                .map(teacher -> {
	                	teacher.setUsername(newTeacher.getUsername());
	                	teacher.setEmail(newTeacher.getEmail());
	                	teacher.setPassword(encoder.encode(newTeacher.getPassword()));
	                	teacher.setFirstName(newTeacher.getFirstName());
	                	teacher.setLastName(newTeacher.getLastName());
	                	teacher.setBranch(newTeacher.getBranch());
	                    return teacherDao.save(teacher);
	                }).orElseThrow(() -> new UserNotFoundException(id));
	    }
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.teacherService.delete(id);
	}
	
	@GetMapping("/getallteachers")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<Teacher>> getAllTeacher(){
		return this.teacherService.getAllTeacher();
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
