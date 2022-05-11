package ee.kaido.webshop.model.request.output;

import lombok.Data;

import java.util.Date;
@Data
public class AuthData {
    private String token;
    private Date expiration;
}
