package ee.kaido.webshop.controller;

import ee.kaido.webshop.authentication.TokenGenerator;
import ee.kaido.webshop.controller.exception.EmailExistsException;
import ee.kaido.webshop.controller.exception.PersonExistsException;
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

import javax.validation.ValidationException;
import java.sql.SQLException;

@RestController
public class AuthenticationController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("signup")
    public ResponseEntity<Boolean> signup(@RequestBody Person person) throws EmailExistsException, PersonExistsException {
        if (personRepository.findById(person.getPersonCode()).isPresent()) {
            throw new PersonExistsException();
        }
        String hashedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);
        try {
            personRepository.save(person);
            return ResponseEntity.ok().body(true);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                throw new EmailExistsException(rootCause.getMessage());
            } else {
                throw new ValidationException(rootCause.getMessage());
            }
        }
    }

    @PostMapping("login")
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
