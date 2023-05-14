package com.examschedulingproject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "admins")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "id")
public class Admin extends User{

	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "phone")
	private String phone;

	public Admin(Long id, String username, String email, String password, String firstName, String lastName, String phone) {
		super.setId(id);
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public Admin(String username, String email, String password, String firstName, String lastName, String phone) {
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	
	
	
	
}
