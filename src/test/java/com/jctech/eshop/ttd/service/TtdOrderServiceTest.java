package com.jctech.eshop.ttd.service;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import com.jctech.eshop.model.order.Order;
import com.jctech.eshop.repo.OrderRepo;
import com.jctech.eshop.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class) 
public class TtdOrderServiceTest {
	
	private Order order;
	
	@Mock
	private OrderRepo orderRepo;
	
	@InjectMocks
	private OrderServiceImpl orderService;
	
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
	public void givenOrder_whenSave_returnSavedOrder() throws Exception { 
		given(orderRepo.save(order)).willReturn(order);
		Order savedOrder = orderService.saveOrUpdateOrder(order);
		Assertions.assertThat(savedOrder).isNotNull();
		Assertions.assertThat(savedOrder.getCustomerId()).isEqualTo(order.getCustomerId());
		
		verify(orderRepo, times(1)).save(order);
	}

}
