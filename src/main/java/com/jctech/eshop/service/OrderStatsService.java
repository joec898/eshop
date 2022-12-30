package com.jctech.eshop.service;

import java.util.List;

import com.jctech.eshop.model.SingleSerise;

public interface OrderStatsService {
	List<SingleSerise> getOrderStats(String orderStatType);
}
