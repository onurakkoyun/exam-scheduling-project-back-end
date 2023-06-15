package com.examschedulingproject.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "course"}) 
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "teacher_id", referencedColumnName = "id")
public class Teacher extends User{
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "branch")
	private String branch;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Course> courses;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	
	/*@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "teacher_courses", 
				joinColumns = @JoinColumn(name = "teacherId"), 
				inverseJoinColumns = @JoinColumn(name = "courseId"))
	List<Course> courses;*/

	public Teacher(Long id, String username, String email, String password, String firstName, String lastName, String branch) {
		super.setId(id);
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.branch = branch;
	}
	
	public Teacher(String username, String email, String password, String firstName, String lastName, String branch) {
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.branch = branch;
	}

	public Teacher(Long id, String username, String email, String password, String firstName, String lastName, String branch, List<Course> courses) {
		super.setId(id);
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.setFirstName(firstName); 
		this.setLastName(lastName);
		this.setBranch(branch);
		this.setCourses(courses);
	}
	
	

	
	
	

}
