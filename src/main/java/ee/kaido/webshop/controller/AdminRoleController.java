package ee.kaido.webshop.controller;
import ee.kaido.webshop.controller.exception.PersonNotFoundException;
import ee.kaido.webshop.model.database.Person;
import ee.kaido.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminRoleController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("person/{personCode}")
    public Person getPersonById(@PathVariable String personCode) throws PersonNotFoundException {
        if (personRepository.findById(personCode).isPresent()) {
            return personRepository.findById(personCode).get();
        } else {
            throw new PersonNotFoundException();
        }
    }

    @PostMapping("add-admin/{personCode}")
    public void addPersonAsAdmin(@PathVariable String personCode) throws PersonNotFoundException {
        if (personRepository.findById(personCode).isPresent()) {
            Person person = personRepository.findById(personCode).get();
            person.setRole("ROLE_ADMIN");
            personRepository.save(person);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @PostMapping("add-super-admin/{personCode}")
    public void addPersonAsSuperAdmin(@PathVariable String personCode) throws PersonNotFoundException {
        if (personRepository.findById(personCode).isPresent()) {
            Person person = personRepository.findById(personCode).get();
            person.setRole("ROLE_SUPER_ADMIN");
            personRepository.save(person);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @GetMapping("get-admins")
    public List<Person> getAdmins() {
        return personRepository.getAllByRole("ROLE_ADMIN");
    }

    @GetMapping("get-super-admins")
    public List<Person> getSuperAdmins() {
        return personRepository.getAllByRole("ROLE_SUPER_ADMIN");
    }

    // http://localhost:8080/delete-role/31
    @DeleteMapping("delete-role/{personCode}")
    public void deleteRoles(@PathVariable String personCode) throws PersonNotFoundException {
        if (personRepository.findById(personCode).isPresent()) {
            Person person = personRepository.findById(personCode).get();
            person.setRole("");
            personRepository.save(person);
        } else {
            throw new PersonNotFoundException();
        }
    }


}
