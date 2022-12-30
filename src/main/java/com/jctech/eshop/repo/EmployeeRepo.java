package com.jctech.eshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jctech.eshop.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
}
