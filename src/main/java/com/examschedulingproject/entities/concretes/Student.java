	package com.examschedulingproject.entities.concretes;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "id")
public class Student extends User{
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "faculty")
	private String faculty;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "degree")
	private int degree;
	
	@PastOrPresent
	@CreationTimestamp
	@Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<CourseRegistration> courseRegistrations;
	
	/*@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "student_courses", 
				joinColumns = @JoinColumn(name = "studentId"), 
				inverseJoinColumns = @JoinColumn(name = "courseId"))
	private List<Course> courses;*/
	
	public Student(Long id, String username, String email, String password, String firstName, String lastName, 
			String faculty, String department, int degree, LocalDateTime registrationDate) {
		super.setId(id);
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.faculty = faculty;
		this.department = department;
		this.degree = degree;
		this.registrationDate = registrationDate;
	}
	
	public Student(String username, String email, String password, String firstName, String lastName, 
			String faculty, String department, int degree, LocalDateTime registrationDate) {
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.faculty = faculty;
		this.department = department;
		this.degree = degree;
		this.registrationDate = registrationDate;
	}

	
	
	

}
