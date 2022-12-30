package com.jctech.eshop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.SingleSerise;

@Service
public class OrderStatsServiceImpl implements OrderStatsService{
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SingleSerise> getOrderStats(String orderStatType) {
		 String fieldName = "";
	        if (orderStatType.equalsIgnoreCase("status") || orderStatType.equalsIgnoreCase("order_status")){
	            fieldName = " order_status ";
	        }
	        else if (orderStatType.equalsIgnoreCase("paytype") || orderStatType.equalsIgnoreCase("payment_type")){
	            fieldName = " payment_type ";
	        }
	        else if (orderStatType.equalsIgnoreCase("country") || orderStatType.equalsIgnoreCase("ship_country")){
	            fieldName = " ship_country ";
	        }
	        else{
	            fieldName = " order_status ";
	        }

	        String sql = "select count(*) as value, " + fieldName + " as name from orders group by " + fieldName;

	        SingleSerise singleSerise;
	        List<SingleSerise> dataItemList = new ArrayList<SingleSerise>();
	        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

	        for (Map<String, Object> row : list) {
	            singleSerise = new SingleSerise((String)row.get("name"), new BigDecimal((long)row.get("value")) );
	            dataItemList.add(singleSerise);
	        }
	        
	        return dataItemList; 
	}

}
