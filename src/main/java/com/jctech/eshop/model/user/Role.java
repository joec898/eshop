package com.jctech.eshop.model.user;

import javax.persistence.*;

import lombok.Data;

@Data
@Table (name = "roles")
@Entity
public class Role {
	
	 @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
	 
	 @Enumerated(EnumType.STRING)
     private ERole name;
     
     public Role() {
    	 
     }
     
	public Role(ERole name) {
		this.name = name;
	}
	
	public static ERole fromString(String text) {
		for (ERole e : ERole.values()) {
			if (e.name().equalsIgnoreCase(text)) {
				return e;
			}
		}
		return null;
	}
}
