package br.com.projeto.ecommerce.dto;

import br.com.projeto.ecommerce.enums.StatusPayment;
import br.com.projeto.ecommerce.models.OrderItem;
import br.com.projeto.ecommerce.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    private User user;

    private List<OrderItem> items;
}
