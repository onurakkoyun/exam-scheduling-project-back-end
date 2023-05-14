package com.examschedulingproject.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupTeacherRequest {
	
	@NotBlank
    @Size(min = 3, max = 20)
	private String firstName;
	
	@NotBlank
    @Size(min = 3, max = 20)
	private String lastName;
	
	@NotBlank
	private String branch;

	@NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
