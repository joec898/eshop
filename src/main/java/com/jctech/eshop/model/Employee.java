package com.jctech.eshop.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="employees")
public class Employee {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String  lastName;
    private String  firstName;
    private String  email;
    private String  avatar;
    private String  jobTitle;
    private String  department;
    private Integer managerId;
    private String  phone;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    private String  postalCode;
    private String  country;
    
    public Employee() {
    	
    }

	public Employee(Integer id, String lastName, String firstName, String email, String avatar, String jobTitle,
			String department, Integer managerId, String phone, String address1, String address2, String city,
			String state, String postalCode, String country) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.avatar = avatar;
		this.jobTitle = jobTitle;
		this.department = department;
		this.managerId = managerId;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
	}

	public Employee(Integer id, String lastName, String firstName, String email, String jobTitle, String phone) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.jobTitle = jobTitle;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", avatar=" + avatar + ", jobTitle=" + jobTitle + ", department=" + department + ", managerId="
				+ managerId + ", phone=" + phone + ", address1=" + address1 + ", address2=" + address2 + ", city="
				+ city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + "]";
	}
	
}
