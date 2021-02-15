package com.devsuoerior.dsdelivery.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuoerior.dsdelivery.dto.OrderDTO;
import com.devsuoerior.dsdelivery.dto.ProductDTO;
import com.devsuoerior.dsdelivery.entities.Order;
import com.devsuoerior.dsdelivery.entities.OrderStatus;
import com.devsuoerior.dsdelivery.entities.Product;
import com.devsuoerior.dsdelivery.repositories.OrderRepository;
import com.devsuoerior.dsdelivery.repositories.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findOdersWithProducts();
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
		
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto){
		Order order = new Order(null, dto.getAddress() , dto.getLatitude() , dto.getLongitude() ,
				Instant.now(), OrderStatus.PENDING);
		for(ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
	order = repository.save(order);
	return new OrderDTO(order);
	
	}

	
	@Transactional
	public OrderDTO setDelivered(Long id ){
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		return new OrderDTO(order);
	}
}
