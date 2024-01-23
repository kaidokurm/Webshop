package ee.kaido.webshop.controller;

import ee.kaido.webshop.model.request.output.ParcelMachines;
import ee.kaido.webshop.service.ParcelMachineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ParcelMachineController {

    @Autowired
    ParcelMachineService parcelMachineService;

    @Operation(description = "Nii Omniva kui Smartpost pakiautomaadi kättesaamine")
    @GetMapping("parcel-machines/{country}")
    public ResponseEntity<ParcelMachines> getParcelMachines(@PathVariable String country) {
        log.info("Taking parcel Machines {}",country);
        country = country.toUpperCase();
        return ResponseEntity.ok()
                .body(parcelMachineService.getParcelMachines(country));
    }

}
