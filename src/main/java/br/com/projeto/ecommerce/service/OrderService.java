package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.enums.StatusOrder;
import br.com.projeto.ecommerce.exceptions.InvalidOrderException;
import br.com.projeto.ecommerce.mapper.OrderMapper;
import br.com.projeto.ecommerce.models.Order;
import br.com.projeto.ecommerce.repository.OrderRepository;
import br.com.projeto.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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

        BigDecimal totalPrice = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalPrice);
        order.setStatus(StatusOrder.PENDING);

        orderRepository.save(order);
        return order;
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
