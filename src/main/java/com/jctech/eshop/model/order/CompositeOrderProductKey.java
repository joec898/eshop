package com.jctech.eshop.model.order;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Data
@Embeddable
public class CompositeOrderProductKey implements Serializable {
	private static final long serialVersionUID = 1L;
	private int orderId;
    private int productId;

    public CompositeOrderProductKey(int orderId, int productId){
        this.orderId   =orderId;
        this.productId =productId;
    }
}
