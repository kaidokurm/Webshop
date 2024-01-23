package ee.kaido.webshop.controller;

import ee.kaido.webshop.cache.ProductCache;
import ee.kaido.webshop.model.database.Product;
import ee.kaido.webshop.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCache productCache;

    @Operation(description = "Get all products")
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @Operation(description = "Add a new product")
    @PostMapping
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)//created()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @Operation(description = "Delete a product with id")
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        productCache.emptyCache();
        productRepository.deleteById(id);
        return ResponseEntity.ok().body(productRepository.getAllByOrderByIdAsc());
    }

    @Operation(description = "Get a product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok()
                    .body(productCache.getProduct(id));
        } catch (Exception e) {
            log.error("No product found");
        }
        return null;
    }

    @Operation(description = "Edit a product")
    @PutMapping
    public List<Product> editProduct(@RequestBody Product product) {
        productCache.emptyCache();
        productRepository.save(product);
        return productRepository.getAllByOrderByIdAsc();
    }

    @Operation(description = "Delete all products")
    @DeleteMapping
    public String deleteAllProduct() {
        productCache.emptyCache();
        productRepository.flush();
        return "Kustutasid k]ik tooted";
    }

    @PostMapping("/increase-stock")
    public ResponseEntity<List<Product>> increaseStock(@RequestBody Product product) throws ExecutionException {
        Product updatedProduct = productCache.getProduct(product.getId());
        log.info(updatedProduct);
        int productStock = updatedProduct.getStock();
        log.info(productStock);
        updatedProduct.setStock(++productStock);
        productRepository.save(updatedProduct);
        productCache.updateCache(updatedProduct);
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @PostMapping("/decrease-stock")
    public ResponseEntity<List<Product>> decreaseStock(@RequestBody Product product) throws ExecutionException {
        Product updatedProduct = productCache.getProduct(product.getId());
        log.info(updatedProduct);
        int productStock = updatedProduct.getStock();
        // TODO: ERRORI kuvamine
        log.info(productStock);
        if (productStock > 0) {
            updatedProduct.setStock(--productStock);
            productRepository.save(updatedProduct);
            productCache.updateCache(updatedProduct);
        }
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }


    @GetMapping("-positive-stock")
    public ResponseEntity<List<Product>> getPositiveStockProducts() {
        return ResponseEntity.ok().body(productRepository
                .getAllByStockGreaterThanOrderByIdAsc(0));
    }

    @GetMapping("-active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok().body(productRepository
                .getAllByStockGreaterThanAndActiveEqualsOrderByIdAsc(0, true));
    }
}
