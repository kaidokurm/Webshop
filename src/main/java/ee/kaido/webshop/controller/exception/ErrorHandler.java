package ee.kaido.webshop.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler //annoteerime, et see funktioon p[[ab kinni exceptioni
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentTypeMismatchException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(new Date());
        response.setMessage("Sisestasid numbri asemel muu s[mboli");
        // http staatus kood
        // timestamp
        // eesti keeles mis juhtus
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(NoSuchElementException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());
        response.setMessage("Otsitud elementi andmebaasist ei leitud");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(HttpRequestMethodNotSupportedException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        response.setHttpStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setTimestamp(new Date());
        response.setMessage("Sellele endpointile see method ei sobi");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

}
