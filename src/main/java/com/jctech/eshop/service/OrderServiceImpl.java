package com.jctech.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.order.Order;
import com.jctech.eshop.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Override
	public Order saveOrUpdateOrder(Order order) {
		return orderRepo.save(order);
	} 

	@Override
	public void deleteOrder(Integer id) {
		orderRepo.deleteById(id);
	}

}
