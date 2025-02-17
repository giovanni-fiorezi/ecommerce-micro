package br.com.projeto.ecommerce.mapper;

import br.com.projeto.ecommerce.dto.ProductDto;
import br.com.projeto.ecommerce.models.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Classe responsável por fazer a conversão de uma Entidade em VO e vice-versa
 */

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Named("toDto")
    ProductDto toDto(Product product);

    @Named("toEntity")
    Product toEntity(ProductDto dto);

    @Named("toDtoList")
    @IterableMapping(qualifiedByName = "toDto")
    List<ProductDto> toDtoList(List<Product> productList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product product);
}

