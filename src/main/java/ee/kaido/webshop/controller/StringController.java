package ee.kaido.webshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController// annoteerimine --- import
public class StringController {

    @GetMapping("hello") //localhost:8080/hello Teeb lehe
    public String getString() {
        return "Hello World";
    }

    //pathvariable -- urlmuutuja
    @GetMapping("string/{returnString}") //localhost:8080/hello Teeb lehe
    public String returnString(@PathVariable String returnString) {//muutuja on sama nimega mis getmappingus
        return returnString;
    }

    //pathvariable -- urlmuutuja
    @GetMapping("arve/{returnLong}/{nr2}") //localhost:8080/hello Teeb lehe
    public long liida(@PathVariable Long returnLong, @PathVariable int nr2) {//muutuja on sama nimega mis getmappingus
        return returnLong + nr2;
    }


}
