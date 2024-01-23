package ee.kaido.webshop.controller;

import ee.kaido.webshop.model.database.Product;
import ee.kaido.webshop.model.request.output.EveryPayUrl;
import ee.kaido.webshop.service.OrderService;
import ee.kaido.webshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("payment")
    public ResponseEntity<EveryPayUrl> getPaymentLink(@RequestBody List<Product> products) {
        // Tooted --- nimedega+hindadega
        // Maksma --- Tellimuse nr-t
        // Salvestan andmebaasi -> maksmata kujul
        // Võtan andmebaasist tema ID (mis on genereeritud)
        // ---> Lähen maksma
        List<Product> originalProducts = orderService.getAllProductsFromDb(products);
        double orderSum = orderService.calculateOrderSum(originalProducts);
        Long id = orderService.saveToDatabase(originalProducts, orderSum);
        return ResponseEntity.ok().body(
                paymentService.getPaymentLink(orderSum, id));
    }

    @PostMapping("check-payment")
    public ResponseEntity<Boolean> checkIfPaid(@RequestParam Long orderId, @RequestParam String paymentRef) {
        return ResponseEntity.ok().body(
                paymentService.checkIfOrderPaid(orderId, paymentRef));
    }
}
