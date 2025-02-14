package br.com.projeto.ecommerce.mapper;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.dto.ProductDto;
import br.com.projeto.ecommerce.models.Order;
import br.com.projeto.ecommerce.models.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> orderList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrderDto dto, @MappingTarget Order order);

}
