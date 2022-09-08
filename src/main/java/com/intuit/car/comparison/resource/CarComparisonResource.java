package com.intuit.car.comparison.resource;

import com.intuit.car.comparison.constant.CarComparisonConstants;
import com.intuit.car.comparison.exception.CarNotFoundException;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.service.CarComparisonService;
import com.intuit.car.comparison.service.CarSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(CarComparisonConstants.CAR_COMPARISON_RESOURCE_PATH)
public class CarComparisonResource {

    @Autowired
    private CarComparisonService carComparisonService;

    @Autowired
    private CarSuggestionService carSuggestionService;

    @GetMapping("/getSuggestion")
    public ResponseEntity<List<Car>> getSuggestion(@RequestParam String carId) throws CarNotFoundException {
         return new ResponseEntity<>(carSuggestionService.getCarSuggestions(carId), HttpStatus.OK);
    }

    @GetMapping("/getCarDetailsForComparison")
    public ResponseEntity<List<Car>> getCarDetailsForComparison(@RequestParam String carId, @RequestParam Set<String> carIdsToCompare, @RequestParam boolean onlyDifference) throws CloneNotSupportedException {
        return new ResponseEntity<>(carComparisonService.getCarFeaturesAndSpecifications(carId, carIdsToCompare, onlyDifference), HttpStatus.OK);
    }
}
