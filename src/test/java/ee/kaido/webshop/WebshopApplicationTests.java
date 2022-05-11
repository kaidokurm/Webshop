package ee.kaido.webshop;

import ee.kaido.webshop.model.database.Product;
import ee.kaido.webshop.repository.ProductRepository;
import org.checkerframework.checker.units.qual.Length;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WebshopApplicationTests {
    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
    }
    @Test
    void checkIfGetProducts(){
        List<Product> products= productRepository.findAll();
        Assertions.assertTrue(products.size()>0);
    }
    @Test
    void checkIfProductAddedInRepository(){
        Product addedProduct = new Product(99L,"Nimi",123.32,null,true,"Descr", 0,null);

        Product savedProduct = productRepository.save(addedProduct);
        Product fetchedProduct = productRepository.findById(savedProduct.getId()).get();
        Assertions.assertEquals(savedProduct.getName(), fetchedProduct.getName());
    }
}
