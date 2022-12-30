package com.jctech.eshop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.order.OrderDetail;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired private 
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<OrderDetail> findOrderDetails(Integer id) {

		String sql = " select order_id, product_id   , customer_id   , order_date, order_status  , shipped_date    , employee_id , payment_type, paid_date, "
				+ " ship_name      , ship_address1, ship_address2 , ship_city , ship_state    , ship_postal_code, ship_country, "
				+ " product_code   , product_name , category      , quantity  , unit_price    , discount        , date_allocated, order_item_status, "
				+ " shipping_fee   , customer_name, customer_email, customer_company from order_details ";
		String where = " where 1 = 1 ";
		String order = " order by order_id, product_id ";
		if (id != null) {
			where = where + " and order_id = " + id;
		}

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		long prevOrderId = -1, newOrderId;
		OrderDetail orderDetail = new OrderDetail();
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql + where + order);
		for (Map<String, Object> row : results) {
			newOrderId = (int) row.get("order_id");
			if (prevOrderId != newOrderId) {

		        orderDetail = new OrderDetail(
		                (int)row.get("order_id"),
		                (Date)row.get("order_date"),
		                (String)row.get("order_status"),
		                (Date)row.get("shipped_date"),
		                (String)row.get("ship_name"),
		                (String)row.get("ship_address1"),
		                (String)row.get("ship_address2"),
		                (String)row.get("ship_city"),
		                (String)row.get("ship_state"),
		                (String)row.get("ship_postal_code"),
		                (String)row.get("ship_country"),
		                (BigDecimal)row.get("shipping_fee"),
		                (Integer)row.get("customer_id"),
		                (String)row.get("customer_name"),
		                (String)row.get("customer_email"),
		                (String)row.get("company"),
		                (String)row.get("payment_type"),
		                (Date)row.get("paid_date"),
		                (int)row.get("employee_id")
		            );
		        
		            orderDetail.addOrderLine(
		                (int)row.get("product_id"),
		                (String)row.get("product_code"),
		                (String)row.get("product_name"),
		                (String)row.get("category"),
		                (BigDecimal)row.get("quantity"),
		                (BigDecimal)row.get("unit_price"),
		                (BigDecimal)row.get("discount"),
		                (Date)row.get("date_allocated"),
		                (String)row.get("order_item_status")
		              );
				 
				prevOrderId = newOrderId;
				
				orderDetails.add(orderDetail);
				
			} else {
				orderDetail.addOrderLine(
				          (int)row.get("product_id"),
				          (String)row.get("product_code"),
				          (String)row.get("product_name"),
				          (String)row.get("category"),
				          (BigDecimal)row.get("quantity"),
				          (BigDecimal)row.get("unit_price"),
				          (BigDecimal)row.get("discount"),
				          (Date)row.get("date_allocated"),
				          (String)row.get("order_item_status")
				        );
			}
		}
	
		return orderDetails;
	}


}
