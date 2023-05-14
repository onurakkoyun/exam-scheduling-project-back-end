package com.examschedulingproject.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupAdminRequest{
	
	@NotBlank
    @Size(min = 3, max = 20)
	private String firstName;
	
	@NotBlank
    @Size(min = 3, max = 20)
	private String lastName;
	
	@NotBlank
    @Size(min = 11, max = 11)
	private String phone;

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
