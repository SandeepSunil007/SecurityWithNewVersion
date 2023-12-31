package com.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security1.jwt.AuthRequest;
import com.security1.jwt.JwtService;
import com.security1.request.EmployeeRequest;
import com.security1.response.EmployeeResponse;
import com.security1.security.entity.UserInfo;
import com.security1.service.EmployeeService;

@RestController
@RequestMapping("api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/add")
	public ResponseEntity<EmployeeResponse> addEmployeeData(@RequestBody EmployeeRequest employeeRequest) {
		return ResponseEntity.ok(EmployeeResponse.builder().message("Data Added Succesfully..")
				.data(employeeService.addEmployee(employeeRequest)).build());

	}

	@GetMapping("/message")
	public String message() {
		return "Hello welcome Message";
	}

	@GetMapping("/get/{integer}")
	@PreAuthorize("hasAuthority('USER')")
	ResponseEntity<EmployeeResponse> getData(@PathVariable Integer integer) {
		return ResponseEntity.ok(EmployeeResponse.builder().message("get the data Succesfully..")
				.data(employeeService.getEmp(integer)).build());
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('ADMIN')")
	ResponseEntity<EmployeeResponse> getData() {
		return ResponseEntity.ok(EmployeeResponse.builder().message("get the data Succesfully..")
				.data(employeeService.getAllData()).build());

	}

	// Security

	
	@PostMapping("/addUser")
	ResponseEntity<EmployeeResponse> addUser(@RequestBody UserInfo info) {
		return ResponseEntity.ok(EmployeeResponse.builder().message(employeeService.addLoginUser(info)).build());

	}
	
	// JWT(JSON Web Token) -- As per the flow based on user details we need to generate the JWT
	
	
	@Autowired
	private JwtService jwtService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest request) {
  // for working authentication from DB users. we need to create a bean explicitly in configuration file
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		if(authenticate.isAuthenticated()) {
			return jwtService.generateToken(request.getUsername());
		}else {
			throw new UsernameNotFoundException("invalid user request !");
		}
		
		
	}
	
	
}
