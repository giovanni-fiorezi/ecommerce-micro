package br.com.projeto.ecommerce.mapper;

import br.com.projeto.ecommerce.dto.ProductDto;
import br.com.projeto.ecommerce.models.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-14T17:18:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Azul Systems, Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setName( product.getName() );
        productDto.setDescription( product.getDescription() );
        productDto.setProductCategory( product.getProductCategory() );
        productDto.setAmount( product.getAmount() );
        productDto.setPrice( product.getPrice() );

        return productDto;
    }

    @Override
    public Product toEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( dto.getName() );
        product.setDescription( dto.getDescription() );
        product.setProductCategory( dto.getProductCategory() );
        product.setAmount( dto.getAmount() );
        product.setPrice( dto.getPrice() );

        return product;
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> productList) {
        if ( productList == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( productList.size() );
        for ( Product product : productList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(ProductDto dto, Product product) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            product.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            product.setDescription( dto.getDescription() );
        }
        if ( dto.getProductCategory() != null ) {
            product.setProductCategory( dto.getProductCategory() );
        }
        if ( dto.getAmount() != null ) {
            product.setAmount( dto.getAmount() );
        }
        if ( dto.getPrice() != null ) {
            product.setPrice( dto.getPrice() );
        }
    }
}
