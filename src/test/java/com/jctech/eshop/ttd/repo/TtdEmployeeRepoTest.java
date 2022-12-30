package com.jctech.eshop.ttd.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.Employee;
import com.jctech.eshop.repo.EmployeeRepo;

/**
 * @author jc
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TtdEmployeeRepoTest {

	private Employee employee;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	public void setUp() {
		employee = new Employee(222, "Hayes", "Rachel", "rhayes1@si.edu", "Manager", "1-(971)797-2729");
	}
	
	@AfterEach
	public void tearDown() {
		employee = null;
	}
	
	@Test
	public void givenEmployee_whenFindById_thenReturnEmployee() throws Exception {
		Employee empSaved = entityManager.persistFlushFind(employee);
		Employee emp = employeeRepo.findById(employee.getId()).get();
		employeeRepo.deleteById(employee.getId());
		
		Assertions.assertThat(emp).isNotNull();
		Assertions.assertThat(emp.getFirstName()).isEqualTo(empSaved.getFirstName());
	}
	
	@Test
	public void givenPage_PageSize_whenFindAll_thenReturnEmployeesInPage() {
		// Pageable p = Pageable.ofSize(10);
		int page = 0;
		int size = 10;
		Pageable p = PageRequest.of(page, size);
		Employee qry = new Employee();
		Page<Employee> pg = employeeRepo.findAll(Example.of(qry), p);

		List<Employee> employees = pg.getContent();
		assertThat(employees.size()).isEqualTo(size);
	}

	@Test
	public void givenCustomer_whenSave_theReturnSavedCustomer() {
		Employee empSaved = employeeRepo.save(employee);
		employeeRepo.delete(empSaved);
		
		assertThat(empSaved).isNotNull();
		assertThat(employee.getEmail()).isEqualTo(empSaved.getEmail());
	}
 
	@Test
	public void givenCustomer_whenDelete_thenRemoveCustomer() {
		Employee empSaved = employeeRepo.save(employee);

		employeeRepo.deleteById(empSaved.getId());
		Optional<Employee> optionalCust = employeeRepo.findById(empSaved.getId());
		assertThat(optionalCust).isEmpty();
	}
	
}
