package br.com.projeto.ecommerce.dto;

import br.com.projeto.ecommerce.enums.ProductCategory;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class ProductDto {

    private String name;
    private String description;
    private ProductCategory productCategory;
    private Integer amount;
    private BigDecimal price;

}
