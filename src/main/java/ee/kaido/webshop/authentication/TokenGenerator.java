package ee.kaido.webshop.authentication;

import ee.kaido.webshop.model.request.output.AuthData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    @Value("${token.key}")
    private String key;

    public AuthData createAuthToken(String email){
//        AuthData authData = new AuthData();
        AuthData authData =new AuthData();
        Date newDate= DateUtils.addHours(new Date(), 5);

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, key)//allkirjastus alati peaks olema TODO: Muuda Muutujaks
                .setIssuer("webshop")//andis selle v'lja
                .setSubject(email)//lisa email peaks ka olema
                .setExpiration(newDate)//lisa
                .compact();//koostab tokeni

        authData.setToken(token);
        authData.setExpiration(newDate);
        return authData;
    }

}
