package ee.kaido.webshop.authentication;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class TokenParser extends BasicAuthenticationFilter {
 //   @Value("${token.key}")
    private String key;
//    //@Value ei saa panna
    public void setKey(String key){
        this.key=key;
    }

    public TokenParser(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //basic - username+password
    //bearer - token, mille sees on mitmeid v''rtusi
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");//saab headerist authorizationi alt
        if (token != null && token.startsWith("Bearer ")) {
//            log.info(token);
            token = token.replace("Bearer ", "");//muudame alguses Beareri ""ga

            Claims claims = Jwts.parser()
                    .setSigningKey(key)//TODO: lisa muutujate alla
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String issuer = claims.getIssuer();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(//principial, kes on loginud
                    email,//unikaalne
                    null,//v]ib muutuda
                    null//]igus
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
//        chain.doFilter(request,response);
                super.doFilterInternal(request, response, chain);
    }
}



