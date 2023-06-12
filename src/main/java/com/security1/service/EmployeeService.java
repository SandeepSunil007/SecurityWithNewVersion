package com.security1.service;

import java.util.List;

import com.security1.entity.Employee;
import com.security1.request.EmployeeRequest;
import com.security1.security.entity.UserInfo;

public interface EmployeeService {
	public Employee addEmployee(EmployeeRequest employeeRequest);
	public Employee getEmp(Integer id);
	public List<Employee> getAllData();
	
	
	public String addLoginUser(UserInfo loginUser);

}
