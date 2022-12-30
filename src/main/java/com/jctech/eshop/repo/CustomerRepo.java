package com.jctech.eshop.repo;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jctech.eshop.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> { 
	@Query(value = "SELECT * FROM customers where first_name = ?", nativeQuery = true)
	public Customer findByFirstName(String firstName); 
 
    public Page<Customer> findAll(Pageable p); 
    //boolean exists( Integer id);
	 
}
