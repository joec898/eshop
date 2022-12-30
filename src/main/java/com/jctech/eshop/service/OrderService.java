package com.jctech.eshop.service;

import com.jctech.eshop.model.order.Order;

public interface OrderService {
	Order saveOrUpdateOrder(Order order); 
	void deleteOrder(Integer id);
}
