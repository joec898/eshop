package com.jctech.eshop.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product { 
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private String productCode;
	private String productName;
	private String description;
	private Long standardCost;
	private Long listPrice;
	private Integer targetLevel;
	private Integer reorderLevel;
	private Integer minimumReorderQuantity;
	private Integer quantityPerUnit;
	private Integer discontinued;	
    @ApiModelProperty(allowableValues = "Camera, Laptop, Tablet, Phone")  
	private String category;  
	
	public Product() {
	}

	public Product(Integer id, String code, String name, Long cost, Long price) {
		super();
		this.id = id;
		this.productCode = code;
		this.productName = name;
		this.standardCost = cost;
		this.listPrice = price; 
	}

	public Product(Integer id, String productCode, String productName, String description, Long standardCost,
			Long listPrice, int targetLevel, int reorderLevel, int minimumReorderQuantity,
			int quantityPerUnit, int discontinued, String category) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.productName = productName;
		this.description = description;
		this.standardCost = standardCost;
		this.listPrice = listPrice;
		this.targetLevel = targetLevel;
		this.reorderLevel = reorderLevel;
		this.minimumReorderQuantity = minimumReorderQuantity;
		this.quantityPerUnit = quantityPerUnit;
		this.discontinued = discontinued;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productCode=" + productCode + ", productName=" + productName + ", description="
				+ description + ", standardCost=" + standardCost + ", listPrice=" + listPrice + ", targetLevel="
				+ targetLevel + ", reorderLevel=" + reorderLevel + ", minimumReorderQuantity=" + minimumReorderQuantity
				+ ", quantityPerUnit=" + quantityPerUnit + ", discontinued=" + discontinued + "]";
	}

}
