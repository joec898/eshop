package com.jctech.eshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jctech.eshop.model.order.OrderInfo;

public interface OrderInfoRepo extends JpaRepository<OrderInfo, Integer>{
}
