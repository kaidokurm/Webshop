package ee.kaido.webshop.repository;

import ee.kaido.webshop.model.database.Order;
import ee.kaido.webshop.model.database.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByPersonOrderByCreationDateDesc(Person person);

}
