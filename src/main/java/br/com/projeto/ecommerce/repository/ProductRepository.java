package br.com.projeto.ecommerce.repository;

import br.com.projeto.ecommerce.enums.ProductCategory;
import br.com.projeto.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductCategory(ProductCategory productCategory);
}
