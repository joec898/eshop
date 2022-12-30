package com.jctech.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.order.OrderInfo;
import com.jctech.eshop.repo.OrderInfoRepo;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
 
	@Autowired
	private OrderInfoRepo orderInfoRepo;
	
	@Override
	public Page<OrderInfo> getOrderInfosInPage(Integer orderId, Integer customerId, 
			Integer employeeId, String orderStatus, Pageable p) {

		OrderInfo qry = new OrderInfo();
		if (orderId != null)     { qry.setOrderId(orderId); }
	    if (customerId != null)  { qry.setCustomerId(customerId); }
	    if (employeeId != null)  { qry.setEmployeeId(employeeId); }
	    if (orderStatus != null) { qry.setOrderStatus(orderStatus);  }
	      
		Page<OrderInfo> pg = orderInfoRepo.findAll(Example.of(qry), p);
		
		return pg;
	}

}
