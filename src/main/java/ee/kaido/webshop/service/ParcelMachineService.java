package ee.kaido.webshop.service;

import ee.kaido.webshop.model.request.output.ParcelMachines;

public interface ParcelMachineService {

    ParcelMachines getParcelMachines(String country);
}

