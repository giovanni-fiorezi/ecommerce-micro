package br.com.projeto.ecommerce.dto;

import br.com.projeto.ecommerce.enums.ProductCategory;


import java.math.BigDecimal;

public class ProductDto {

    private String name;
    private String description;
    private ProductCategory productCategory;
    private Integer amount;
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(String name, String description, ProductCategory productCategory, Integer amount, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
