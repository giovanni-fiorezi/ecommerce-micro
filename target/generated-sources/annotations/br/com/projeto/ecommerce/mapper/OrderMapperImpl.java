package br.com.projeto.ecommerce.mapper;

import br.com.projeto.ecommerce.dto.OrderDto;
import br.com.projeto.ecommerce.models.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-14T17:17:05-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Azul Systems, Inc.)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        return orderDto;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> orderList) {
        if ( orderList == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orderList.size() );
        for ( Order order : orderList ) {
            list.add( toDto( order ) );
        }

        return list;
    }
}
