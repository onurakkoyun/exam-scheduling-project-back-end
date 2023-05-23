package com.examschedulingproject.entities.concretes;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "exams")
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
	
	@Id
	@Column(name = "exam_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "exam_type")
	private String examType;
	
	@Future
	@Column(name = "exam_date")
	private LocalDate examDate;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;
	
	/*@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="registration_id")	
	private CourseRegistration courseRegistration;*/
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_id")	
	private Course course;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="registration_id")	
	private CourseRegistration courseRegistration;
	

}
