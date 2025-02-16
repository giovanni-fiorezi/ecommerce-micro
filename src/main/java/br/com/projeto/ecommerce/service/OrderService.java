package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.enums.StatusOrder;
import br.com.projeto.ecommerce.exceptions.InvalidOrderException;
import br.com.projeto.ecommerce.mapper.OrderMapper;
import br.com.projeto.ecommerce.models.Order;
import br.com.projeto.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<OrderDto> findOrdersByUser(String userId) {
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("UserId not be null");
        }

        List<OrderDto> ordersList = orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        if(ordersList.isEmpty()) {
            System.out.println("User não fez nenhum pedido ainda");
        }
        
        return ordersList;
    }

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderDto dto) {
        if(Objects.isNull(dto)) {
            throw new IllegalArgumentException("Body of order not be null");
        }

        Order order = OrderMapper.INSTANCE.toEntity(dto);
        orderRepository.save(order);
        order.setStatus(StatusOrder.PENDING);
        return order;

        //Todo -> fazer cálculo para somar todos os itens do carrinho
    }

    @Transactional(rollbackFor = Exception.class)
    public Order updateOrder(OrderDto dto, Long orderId) {
        if(Objects.isNull(dto)) {
            throw new IllegalArgumentException("Body of order not be null");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidOrderException("Order not found with ID " + orderId));

        OrderMapper.INSTANCE.updateEntityFromDto(dto, order);
        Order updateOrder = orderRepository.save(order);
        return updateOrder;
    }

    public String orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + orderId));

        order.setStatus(StatusOrder.CANCELED);
        return "O seu pedido foi cancelado";
    }




}
