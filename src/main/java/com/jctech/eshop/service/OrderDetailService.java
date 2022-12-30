package com.jctech.eshop.service;
 
import java.util.List;

import com.jctech.eshop.model.order.OrderDetail;

public interface OrderDetailService {
	List<OrderDetail> findOrderDetails(Integer id);
}
