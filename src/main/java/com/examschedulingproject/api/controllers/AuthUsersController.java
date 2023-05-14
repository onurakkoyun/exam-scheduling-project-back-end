package com.examschedulingproject.api.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.examschedulingproject.dataAccess.abstracts.IAdminDao;
import com.examschedulingproject.dataAccess.abstracts.RoleDao;
import com.examschedulingproject.dataAccess.abstracts.IStudentDao;
import com.examschedulingproject.dataAccess.abstracts.ITeacherDao;
import com.examschedulingproject.dataAccess.abstracts.UserDao;
import com.examschedulingproject.entities.concretes.Admin;
import com.examschedulingproject.entities.concretes.ERole;
import com.examschedulingproject.entities.concretes.Role;
import com.examschedulingproject.entities.concretes.Student;
import com.examschedulingproject.entities.concretes.Teacher;
import com.examschedulingproject.entities.concretes.User;
import com.examschedulingproject.payload.request.LoginRequest;
import com.examschedulingproject.payload.request.SignupAdminRequest;
import com.examschedulingproject.payload.request.SignupRequest;
import com.examschedulingproject.payload.request.SignupStudentRequest;
import com.examschedulingproject.payload.request.SignupTeacherRequest;
import com.examschedulingproject.payload.response.JwtResponse;
import com.examschedulingproject.payload.response.MessageResponse;
import com.examschedulingproject.security.jwt.JwtUtils;
import com.examschedulingproject.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthUsersController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDao userDao;
	
	@Autowired
	IAdminDao adminDao;
	
	@Autowired
	ITeacherDao teacherDao;
	
	@Autowired
	IStudentDao studentDao;

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

	@PostMapping("/signup/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userDao.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDao.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User newUser = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

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
				case "teacher":
					Role teacherRole = roleDao.findByName(ERole.ROLE_TEACHER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(teacherRole);
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
		newUser.setRoles(roles);
		userDao.save(newUser);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/signup/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupAdminRequest signUpAdminRequest) {
		if (userDao.existsByUsername(signUpAdminRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDao.existsByEmail(signUpAdminRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		Admin newAdmin = new Admin(signUpAdminRequest.getUsername(), signUpAdminRequest.getEmail(),
				encoder.encode(signUpAdminRequest.getPassword()),signUpAdminRequest.getFirstName(), signUpAdminRequest.getLastName() , 
				signUpAdminRequest.getPhone());

		Set<String> strRoles = signUpAdminRequest.getRole();
		Set<Role> roles = new HashSet<>();

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
				case "teacher":
					Role teacherRole = roleDao.findByName(ERole.ROLE_TEACHER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(teacherRole);
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
		newAdmin.setRoles(roles);
		adminDao.save(newAdmin);

		return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
	}
	
	@PostMapping("/signup/teacher")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerTeacher(@Valid @RequestBody SignupTeacherRequest signUpTeacherRequest) {
		if (userDao.existsByUsername(signUpTeacherRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDao.existsByEmail(signUpTeacherRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		Teacher newTeacher = new Teacher(signUpTeacherRequest.getUsername(), signUpTeacherRequest.getEmail(),
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
		newTeacher.setRoles(roles);
		teacherDao.save(newTeacher);
		

		return ResponseEntity.ok(new MessageResponse("Teacher registered successfully!"));
	}
	
	@PostMapping("/signup/student")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerStudent(@Valid @RequestBody SignupStudentRequest signUpStudentRequest) {
		if (userDao.existsByUsername(signUpStudentRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDao.existsByEmail(signUpStudentRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		Student newStudent = new Student(signUpStudentRequest.getUsername(), signUpStudentRequest.getEmail(),
				encoder.encode(signUpStudentRequest.getPassword()),signUpStudentRequest.getFirstName(), signUpStudentRequest.getLastName() , 
				signUpStudentRequest.getFaculty(), signUpStudentRequest.getDepartment(), signUpStudentRequest.getDegree(), signUpStudentRequest.getRegistrationDate());
		
		Set<String> strRoles = signUpStudentRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		Role studentRole = roleDao.findByName(ERole.ROLE_STUDENT)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(studentRole);

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
				case "teacher":
					Role teacherRole = roleDao.findByName(ERole.ROLE_TEACHER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(teacherRole);
					break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		newStudent.setRoles(roles);
		studentDao.save(newStudent);
		

		return ResponseEntity.ok(new MessageResponse("Student registered successfully!"));
	}

}
