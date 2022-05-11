package ee.kaido.webshop.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ListController {
    List<String> strings = new ArrayList<>(Arrays.asList("Minu", "Nimi", "on", "Kaido"));

    @GetMapping("get-strings")//localhost:8080/get-strings
    public List<String> getStrings() {
        return strings;
    }

    //saab ka getmappinguga teha, aga postmapping ei t;;ta browseris
    @PostMapping("add-strings/{newString}")//localhost:8080/get-strings
    public void addString(@PathVariable String newString) {
        strings.add(newString);
    }

    @DeleteMapping("delete-string/{index}")
    public void addString(@PathVariable int index) {
        strings.remove(index);
    }
}

