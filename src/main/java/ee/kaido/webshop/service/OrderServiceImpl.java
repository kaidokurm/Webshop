package ee.kaido.webshop.service;

import ee.kaido.webshop.cache.ProductCache;
import ee.kaido.webshop.model.database.Order;
import ee.kaido.webshop.model.database.PaymentState;
import ee.kaido.webshop.model.database.Product;
import ee.kaido.webshop.repository.OrderRepository;
import ee.kaido.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
@Log4j2
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCache productCache;

    public double calculateOrderSum(List<Product> products) {
        return products.stream()
                                //p->p.getPrice() on sama mis Product::getPrice
                .mapToDouble(Product::getPrice)//map (asendab) k]ik tooted hinnaga (double kujul)
                .sum();

//        double sum=0;
//        for (Product p: products){
//            sum+=p.getPrice();
//        }
//        return sum;
    }

    public Long saveToDatabase(List<Product> products, double orderSum) {
        Order order = new Order();
        order.setOrderSum(orderSum);
        order.setProducts(products);
        order.setPaymentState(PaymentState.INITIAL);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    public List<Product> getAllProductsFromDb(List<Product> products) {
//        List<Product> originalProducts = new ArrayList<>();
//        for (Product p: products) {
//            Long productId = p.getId();
//            Product originalProduct = productRepository.findById(productId).get();
//            originalProducts.add(originalProduct);
//        }
//        return originalProducts;
        return products.stream()//avab voolu
                .map(p -> {
                    try {
                        return productCache.getProduct(p.getId());
                    } catch (ExecutionException e) {
                        log.error("Cache error {}", e.getMessage());
                        return null;
                    }
                })//v]tab k]ik ja v]tab k]ik 'ra
                .collect(Collectors.toList());

    }
}
