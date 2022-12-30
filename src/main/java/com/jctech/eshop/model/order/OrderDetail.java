package com.jctech.eshop.model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDetail extends Order {

	private String customerName;
	private String customerEmail;
	private String customerCompany;
	private List<OrderLine> orderLine;

	public OrderDetail() {
	}

	public OrderDetail(
	        Integer orderId,
	        Date orderDate,
	        String orderStatus,
	        Date   shippedDate,
	        String shipName,
	        String shipAddress1,
	        String shipAddress2 ,
	        String shipCity,
	        String shipState,
	        String shipPostalCode,
	        String shipCountry,
	        BigDecimal shippingFee,
	        Integer customerId,
	        String customerName,
	        String customerEmail,
	        String customerCompany,
	        String paymentType,
	        Date paidDate,
	        Integer employeeId
	    ){
	        super(orderId, employeeId, customerId, orderDate, orderStatus, shippedDate ,shipName, 
	        		shipAddress1,shipAddress2, shipCity, shipState, shipPostalCode, shipCountry, shippingFee, paymentType, paidDate);
	        this.customerName = customerName;
	        this.customerEmail     = customerEmail;
	        this.customerCompany   = customerCompany;
	        this.orderLine         = new ArrayList<OrderLine>();
	    }
	
	public void addOrderLine(int productId, String productCode, String productName, String category,
			BigDecimal quantity, BigDecimal unitPrice, BigDecimal discount, Date dateAllocated,
			String orderItemStatus) {
		OrderLine line = new OrderLine(productId, productCode, productName, category, quantity, 
				unitPrice, discount,dateAllocated, orderItemStatus);
		this.orderLine.add(line);
	}
}


@Data
class OrderLine {

	private int productId;
	private String productCode;
	private String productName;
	private String category;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private BigDecimal discount;
	private Date dateAllocated;
	@ApiModelProperty(allowableValues = "On Order, Allocated, No Stock")
	private String orderItemStatus;

	public OrderLine(int productId, String productCode, String productName, String category, BigDecimal quantity,
			BigDecimal unitPrice, BigDecimal discount, Date dateAllocated, String orderItemStatus) {
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.category = category;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.dateAllocated = dateAllocated;
		this.orderItemStatus = orderItemStatus;
	}

}
