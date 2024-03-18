package ee.katrina.fooddeliveryapplication.controller;

import ee.katrina.fooddeliveryapplication.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class FeeController {

    public static final String FEE_PATH = "/api/v1/fee";

    @Autowired
    FeeService feeService;

    @GetMapping(value = FEE_PATH)
    public ResponseEntity getFee(@PathVariable String city, @PathVariable String vehicleType) throws Exception {
        double fee = feeService.getFee(city, vehicleType);
        return new ResponseEntity(fee, HttpStatus.OK);
    }
}
