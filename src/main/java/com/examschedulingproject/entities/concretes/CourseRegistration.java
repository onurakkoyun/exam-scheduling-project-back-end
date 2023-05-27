package com.examschedulingproject.entities.concretes;

import java.time.LocalDateTime;
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
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "course_registration")
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistration {
	
	@Id
	@Column(name = "registration_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
	private Student student;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;
    
    @JsonIgnore
	@OneToMany(mappedBy = "courseRegistration", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Exam> exams;

    @PastOrPresent
	@CreationTimestamp
    @Column(name = "registeredAt", updatable = false)
    private LocalDateTime registeredAt;

}
