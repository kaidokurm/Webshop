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

    @PostMapping("payment")  // localhost:8080/payment    Body    80   text
    public ResponseEntity<EveryPayUrl> getPaymentLink(@RequestBody List<Product> products) {
        // Tooted --- nimedega+hindadega
        // Maksma --- Tellimuse nr-t
        // Salvestan andmebaasi -> maksmata kujul
        // Võtan andmebaasist tema ID (mis on genereeritud)
        // ---> Lähen maksma
        List<Product> originalProducts = orderService.getAllProductsFromDb(products);
        double orderSum = orderService.calculateOrderSum(originalProducts);
        Long id = orderService.saveToDatabase(originalProducts, orderSum);
        return ResponseEntity.ok().body(paymentService.getPaymentLink(orderSum, id));
    }

    // order_reference=5413137&payment_reference=d26ba3ef85e607e4131f552b4977441c4c865427497f6183adbde78b6ccc68a2
    // order_reference=5413138&payment_reference=5d9f591cd34f68da654fa8bdd3ad9550de094e41050a28fe74c66143181a55bf
    @PostMapping("check-payment")
    public ResponseEntity<Boolean> checkIfPaid(@RequestParam Long orderId, @RequestParam String paymentRef) {
        // Kui on makstud, muudan andmebaasis makstuks
        return ResponseEntity.ok().body(paymentService.checkIfOrderPaid(orderId, paymentRef));
    }
}
