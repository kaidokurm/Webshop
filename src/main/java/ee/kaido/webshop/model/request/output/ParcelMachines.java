package ee.kaido.webshop.model.request.output;

import ee.kaido.webshop.model.request.input.OmnivaParcelMachine;
import ee.kaido.webshop.model.request.input.SmartpostParcelMachine;
import lombok.Data;

import java.util.List;

@Data
public class ParcelMachines {
    private List<OmnivaParcelMachine> omnivaParcelMachines;
    private List<SmartpostParcelMachine> smartpostParcelMachines;
}
