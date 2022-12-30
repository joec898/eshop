package com.jctech.eshop.ttd.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.jctech.eshop.model.order.Order;
import com.jctech.eshop.repo.OrderRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/
@ActiveProfiles("test")
public class TtdOrderRepoTest {
 
	private Order order;
	
	@Autowired
	private OrderRepo orderRepo;
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	DateTime orderDate = DateTime.parse("4/5/2022", formatter);
	DateTime shippedDate = DateTime.parse("4/6/2022", formatter);
	@BeforeEach
	public void setUp() {		
		order = new Order(9001, 204, 40, orderDate.toDate(),  "On Hold", shippedDate.toDate(), 
				"Jean Fuller", "93 Spohn Place", null, "Manggekompo", null, null, "Indonesia", 
				BigDecimal.valueOf(8.1), "Card", orderDate.toDate());
	}
	
	@Test
	public void givenOrderId_whenSave_thenReturnSavedOrder() throws Exception {
		Order savedOrder = orderRepo.save(order);
		orderRepo.deleteById(order.getId());
		
		Assertions.assertThat(savedOrder).isNotNull();
		Assertions.assertThat(savedOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());
	}
	
	@Test
	public void givenCustomer_whenDelete_thenCustomerIsRemoved() {
		Order savedOrder = orderRepo.save(order);

		orderRepo.deleteById(savedOrder.getId());
		Optional<Order> optionalOrder = orderRepo.findById(savedOrder.getId());
		assertThat(optionalOrder).isEmpty();
	}
}
