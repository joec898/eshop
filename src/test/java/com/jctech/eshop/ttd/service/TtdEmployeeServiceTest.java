package com.jctech.eshop.ttd.service;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jctech.eshop.model.Employee;
import com.jctech.eshop.repo.EmployeeRepo;
import com.jctech.eshop.service.EmployeeServiceImpl;

/**
 * @author jc
 */

@ExtendWith(MockitoExtension.class)
public class TtdEmployeeServiceTest {

	private Employee employee;

	@Mock
	private EmployeeRepo empRepo;

	@InjectMocks
	private EmployeeServiceImpl empService;

	@BeforeEach
	public void setUp() {
		employee = new Employee(202, "Hayes", "Rachel", "rhayes1@si.edu", "Manager", "1-(971)797-2729");
		// empService = new EmployeeServiceImpl(empRepo);  // when @InjectMocks is not used
	}

	@Test
	public void givenEmployee_whenGetById_returnEmployee() throws Exception {
		given(empRepo.findById(employee.getId())).willReturn(Optional.of(employee));
		Employee emp = empService.findEmployeeById(employee.getId());

		Assertions.assertThat(emp).isNotNull();
		Assertions.assertThat(emp.getFirstName()).isEqualTo(employee.getFirstName());
	}
	
	@Test
	public void givenEmployee_whenSave_returnSavedEmployee() throws Exception {
		given(empRepo.save(employee)).willReturn(employee);
		Employee emp = empService.saveOrUpdateEmployee(employee);
		
		Assertions.assertThat(emp).isNotNull();
		Assertions.assertThat(emp.getFirstName()).isEqualTo(employee.getFirstName());
	}
	
//	@Test
//	public void givenId_whenDelete_sss() throws Exception {
//		doNothing().when(empService).deleteEmployeeById(employee.getId());
//		empService.deleteEmployeeById(employee.getId());
//		
//		verify(empService, times(1)).deleteEmployeeById(employee.getId());
//	}
}
