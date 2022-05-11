package ee.kaido.webshop.controller;

import ee.kaido.webshop.authentication.TokenGenerator;
import ee.kaido.webshop.model.database.Person;
import ee.kaido.webshop.model.request.input.LoginData;
import ee.kaido.webshop.model.request.output.AuthData;
import ee.kaido.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("signup")
    public ResponseEntity<Boolean> signup(@RequestBody Person person) {
//        person.setPassword();hashimine
//        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();//cr[ptimine
        String hashedPassword = passwordEncoder.encode(person.getPassword());//teeme h'situd passwordi
        person.setPassword(hashedPassword);//salvestme hasitud
        personRepository.save(person);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("login")//{email:"m@m.ee", "password":123}
    public ResponseEntity<AuthData> login(@RequestBody LoginData loginData) {
        Person person = personRepository.getByEmail(loginData.getEmail());
        boolean matches = passwordEncoder.matches(loginData.getPassword(), person.getPassword());
        if (matches) {
            AuthData authData = tokenGenerator.createAuthToken(loginData.getEmail());
            return ResponseEntity.ok().body(authData);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
