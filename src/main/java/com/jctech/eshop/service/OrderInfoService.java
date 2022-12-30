package com.jctech.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.order.OrderInfo;

public interface OrderInfoService {
	Page<OrderInfo> getOrderInfosInPage(Integer orderId, Integer customerId, 
			Integer employeeId, String orderStatus, Pageable p);
}