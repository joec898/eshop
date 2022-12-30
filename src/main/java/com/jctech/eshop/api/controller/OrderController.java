package com.jctech.eshop.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jctech.eshop.model.SingleSerise;
import com.jctech.eshop.model.order.OrderDetail;
import com.jctech.eshop.model.order.OrderDetailResponse;
import com.jctech.eshop.model.order.OrderInfo;
import com.jctech.eshop.model.order.OrderInfoResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;
import com.jctech.eshop.model.response.SingleDataSeriseResponse;
import com.jctech.eshop.service.OrderDetailService;
import com.jctech.eshop.service.OrderInfoService;
import com.jctech.eshop.service.OrderStatsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Orders"})
@RestController
@RequestMapping("/api")
public class OrderController {
	  
	  @Autowired 
	  private OrderInfoService orderInfoService;
	  
	  @Autowired
	  private OrderDetailService orderDetailService;
	  
	  @Autowired 
	  private OrderStatsService orderStatsService;
	  
	  @ApiOperation(value = "List of orders", response = OrderInfoResponse.class)
	  @GetMapping("/orders")
	  public OrderInfoResponse getOrderInfosInPage(
			    @ApiParam(value = ""    )               
			    @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) 
			    Integer page,
			    @ApiParam(value = "between 1 to 1000" )
			    @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) 
			    Integer size,
			    @RequestParam(value = "orderid"     , required = false) 
			    Integer orderId,
			    @RequestParam(value = "customerid"  , required = false) 
			    Integer customerId,
			    @RequestParam(value = "employeeid"  , required = false) 
			    Integer employeeId,
			    @RequestParam(value = "status"      , required = false) 
			    String  orderStatus,
			    Pageable pageable
			  ) {
		  OrderInfoResponse resp = new OrderInfoResponse();
		  Page<OrderInfo> pg =
		       orderInfoService.getOrderInfosInPage(orderId, customerId, employeeId, orderStatus, pageable);
		  resp.setPageStats(pg, true);
	      resp.setItems(pg.getContent()); 
	      
		  return resp;
	  }
	  
	  @ApiOperation(value = "Order Details", response = OrderDetailResponse.class)
      @GetMapping("/order-details")
	  public OrderDetailResponse getOrderDetail( @RequestParam(value = "orderid", required = false) Integer orderId) {
		  OrderDetailResponse resp = new OrderDetailResponse();
		  
		  List<OrderDetail> orderDetails = orderDetailService.findOrderDetails(orderId);
		  
		  resp.setItems(orderDetails);
		  resp.setPageTotal(orderDetails.size(), true);
		  return resp;
	  }
	  
	  @ApiOperation(value = "Order Stats", response = SingleDataSeriseResponse.class)
	  @GetMapping("/order-stats/{type}")
	  public SingleDataSeriseResponse getOrderStats(@PathVariable("type") String type ) {
		  
		  SingleDataSeriseResponse resp = new SingleDataSeriseResponse();
		  List<SingleSerise> orderStats = orderStatsService.getOrderStats(type);
		  resp.setItems(orderStats);
	      resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	      resp.setOperationMessage("Orders by " + type);
		  return resp;
	  }
}
