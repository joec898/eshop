package com.jctech.eshop.repo;

//import reactor.core.publisher.Mono;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jctech.eshop.model.Customer;

@Repository
//public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	//Mono<Customer> findById(Integer id); 

	//public Customer findById(Integer id);
	
	@Query(value = "SELECT * FROM customers where firstName = ?", nativeQuery = true)
	public Customer findByFirstName(String firstName); 
	 
}
