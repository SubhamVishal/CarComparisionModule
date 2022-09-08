package com.intuit.car.comparison.resource;

import com.intuit.car.comparison.constant.CarComparisonConstants;
import com.intuit.car.comparison.exception.CarAlreadyPresentException;
import com.intuit.car.comparison.exception.CarNotFoundException;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CarComparisonConstants.CAR_COMPARISON_RESOURCE_PATH)
public class CarResource {
    @Autowired
    private CarService carService;

    @PostMapping("/addCar")
    public ResponseEntity<Boolean> addCar(@RequestBody Car car) throws CarAlreadyPresentException {
        return new ResponseEntity<>(carService.addCar(car), HttpStatus.OK);
    }

    @PutMapping("/updateCar")
    public ResponseEntity<Boolean> updateCar(@RequestBody Car car) throws CarNotFoundException {
        return new ResponseEntity<>(carService.updateCar(car), HttpStatus.OK);
    }

    @DeleteMapping("/removeCar")
    public ResponseEntity<Boolean> removeCar(@RequestBody Car car) throws CarNotFoundException {
        return new ResponseEntity<>(carService.removeCar(car.getId()), HttpStatus.OK);
    }
}
