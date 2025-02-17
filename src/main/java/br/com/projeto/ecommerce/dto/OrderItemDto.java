package br.com.projeto.ecommerce.dto;

import br.com.projeto.ecommerce.models.Order;
import br.com.projeto.ecommerce.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private Order order;

    private Product product;

    private BigDecimal unitPrice;

    private Integer quantity;
}
