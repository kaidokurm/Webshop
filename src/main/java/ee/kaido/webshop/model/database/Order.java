package ee.kaido.webshop.model.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")//postqres user ja order tabelid on reserveeritud
@SequenceGenerator(name = "seq", initialValue = 543121, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;
    private double orderSum;
    private PaymentState paymentState;

    @ManyToMany
    private List<Product> products;

    //@onetoMany
    //Location List<timeSlot> [ks timeslot on ainult [hes asukohas
    //@ManyToOne
    //Timslot Location
}
