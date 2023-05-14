package com.examschedulingproject.api.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examschedulingproject.dataAccess.abstracts.RoleDao;
import com.examschedulingproject.dataAccess.abstracts.ITeacherDao;
import com.examschedulingproject.entities.concretes.ERole;
import com.examschedulingproject.entities.concretes.Role;
import com.examschedulingproject.entities.concretes.Teacher;
import com.examschedulingproject.payload.request.LoginRequest;
import com.examschedulingproject.payload.request.SignupTeacherRequest;
import com.examschedulingproject.payload.response.JwtResponse;
import com.examschedulingproject.payload.response.MessageResponse;
import com.examschedulingproject.security.jwt.JwtUtils;
import com.examschedulingproject.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/teacher")
public class AuthTeachersController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ITeacherDao teacherDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	/*@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupTeacherRequest signUpTeacherRequest) {
		if (teacherDao.existsByUsername(signUpTeacherRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (teacherDao.existsByEmail(signUpTeacherRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
*/
		// Create new user's account
		/*User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));*/
		
		/*Teacher teacher = new Teacher(signUpTeacherRequest.getUsername(), signUpTeacherRequest.getEmail(),
				encoder.encode(signUpTeacherRequest.getPassword()),signUpTeacherRequest.getFirstName(), signUpTeacherRequest.getLastName() , 
				signUpTeacherRequest.getBranch());

		Set<String> strRoles = signUpTeacherRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		Role teacherRole = roleDao.findByName(ERole.ROLE_TEACHER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(teacherRole);

		if (strRoles == null) {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "student":
					Role studentRole = roleDao.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(studentRole);
			break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		teacher.setRoles(roles);
		teacherDao.save(teacher);
		

		return ResponseEntity.ok(new MessageResponse("Teacher registered successfully!"));
	}*/

}
