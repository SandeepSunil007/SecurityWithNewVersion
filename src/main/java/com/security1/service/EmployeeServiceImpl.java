package com.security1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security1.entity.Employee;
import com.security1.repository.EmployeeRepository;
import com.security1.request.EmployeeRequest;
import com.security1.security.entity.UserInfo;
import com.security1.security.repository.UserInfoRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee addEmployee(EmployeeRequest employeeRequest) {
		return employeeRepository.save(
				Employee.builder().empName(employeeRequest.getEmpName()).salary(employeeRequest.getSalary()).build());
	}

	@Override
	public Employee getEmp(Integer id) {
		return employeeRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Employee> getAllData() {
		return employeeRepository.findAll();
	}

	// Security

	@Autowired
	private UserInfoRepository infoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String addLoginUser(UserInfo loginUser) {
		loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
		infoRepository.save(loginUser);
		return "User Added Into Specified Database..!";
	}

}
