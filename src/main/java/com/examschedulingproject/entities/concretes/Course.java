package com.examschedulingproject.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "exam"}) 
public class Course{
	
	@Id
	@Column(name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "course_code", unique = true)
	private String courseCode;
	
	@Column(name = "course_name")
	private String courseName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private List<Exam> exams;
	
	@NotNull(message = "Teacher must not be null!")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="teacher_id")	
	private Teacher teacher;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CourseRegistration> courseRegistrations;
	
	/*@ManyToMany(mappedBy = "courses")
	private List<Student> students;*/
	
	/*@ManyToMany(mappedBy = "courses")
	List<Teacher> teacher;*/

	
}
