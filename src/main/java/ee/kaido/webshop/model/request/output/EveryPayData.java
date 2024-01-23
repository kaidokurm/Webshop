package ee.kaido.webshop.model.request.output;

import lombok.Data;

@Data
public class EveryPayData {
    private String api_username;
    private String account_name;
    private double amount;
    private String order_reference;
    private String nonce;
    private String timestamp;
    private String customer_url;
}
