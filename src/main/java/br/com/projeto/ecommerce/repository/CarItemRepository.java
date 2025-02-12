package br.com.projeto.ecommerce.repository;

import br.com.projeto.ecommerce.models.CarItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarItemRepository extends JpaRepository<CarItem, Long> {
}
