package ee.kaido.webshop.model.database;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Schema(description = "ID")
    private String personCode;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private String role;
}
