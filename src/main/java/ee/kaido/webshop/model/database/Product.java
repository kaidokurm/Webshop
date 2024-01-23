package ee.kaido.webshop.model.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private double price;
    private String imgSrc;
    private boolean active;
    private String description;

    private int stock = 0;

    @OneToOne
    private Subcategory Subcategory;
}

