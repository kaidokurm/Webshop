package ee.kaido.webshop.configuration;

import ee.kaido.webshop.authentication.TokenParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter sees on authmanager

    @Value("${token.key}")
    private String key;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);//saadab login lehele
        TokenParser tokenParser = new TokenParser(authenticationManager());
        tokenParser.setKey(key);
        http
                .cors().and().headers().xssProtection().disable().and()
                .csrf().disable()
                .addFilter(tokenParser)//peab olema enne teisi
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/products").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers(HttpMethod.POST,"/signup").permitAll()
                .anyRequest()
                .authenticated();
    }
}
