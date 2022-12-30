package com.jctech.eshop.model;

import javax.persistence.*;  

import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	private Integer id;
    private String lastName;
    private String firstName;
    private String email;
    private String company;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    
    public Customer(){}

	public Customer(Integer id, String lastName, String firstName, String email, String company, String phone,
			String address1, String address2, String city, String state, String postalCode, String country) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.company = company;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
	}
	
	public Customer(Integer id, String lastName, String firstName, String email, String company, String phone) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.company = company;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", company=" + company + ", phone=" + phone + ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + "]";
	}

}
