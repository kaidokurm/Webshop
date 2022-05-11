package ee.kaido.webshop.repository;

import ee.kaido.webshop.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {//extend lisab k]ik vidinad interface juurde
        // Product existsProductByActive(boolean active);
        }
