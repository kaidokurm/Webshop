package ee.kaido.webshop.model.request.input;

import ee.kaido.webshop.model.database.Product;
import lombok.Data;

@Data
public class CartProduct {
    private Product product;
    private int quantity;
}
