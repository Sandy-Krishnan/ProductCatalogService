package com.sandy.productcatalogservice.repositories;

import com.sandy.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    @Override
    List<Product> findAll();

    @Override
     Product save(Product product);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT p.description FROM Product p WHERE p.id = :id")
    String getDescriptionWhereIdIs(Long id);
}
