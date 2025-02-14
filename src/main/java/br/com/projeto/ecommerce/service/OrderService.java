package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.mapper.OrderMapper;
import br.com.projeto.ecommerce.models.Order;
import br.com.projeto.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return OrderMapper.INSTANCE.toDtoList(orders);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }
}
