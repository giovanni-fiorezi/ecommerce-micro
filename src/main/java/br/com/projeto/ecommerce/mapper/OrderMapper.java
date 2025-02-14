package br.com.projeto.ecommerce.mapper;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> orderList);
}
