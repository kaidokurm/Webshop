package ee.kaido.webshop.repository;


import ee.kaido.webshop.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {//extend lisab k]ik vidinad interface juurde
   // Product existsProductByActive(boolean active);
    List<Product> getAllByOrderByIdAsc();
    List<Product> getAllByStockGreaterThanOrderByIdAsc(int stock);
    List<Product> getAllByStockGreaterThanAndActiveEqualsOrderByIdAsc(int stock, boolean active);
}
