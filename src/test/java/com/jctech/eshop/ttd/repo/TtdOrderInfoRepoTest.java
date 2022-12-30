package com.jctech.eshop.ttd.repo;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.order.OrderInfo;
import com.jctech.eshop.repo.OrderInfoRepo;

/**
 * @author jc
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TtdOrderInfoRepoTest {
	
	@Autowired
	private OrderInfoRepo orderInfoRepo;
	
	@Test
	public void givenOrderId_whenGetAllInPage_thenReturnOrderInfosOfPage() throws Exception {

		int page = 0;
		int size = 5; 
		Pageable p = PageRequest.of(page, size);
		OrderInfo qry = new OrderInfo();
		Page<OrderInfo> result = orderInfoRepo.findAll(Example.of(qry),p);
		
		List<OrderInfo> list = result.getContent();
		Assertions.assertThat(list.size()).isEqualTo(size);
	}

}
