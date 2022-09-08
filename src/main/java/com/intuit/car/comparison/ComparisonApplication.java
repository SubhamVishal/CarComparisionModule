package com.intuit.car.comparison;

import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.store.CarStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ComparisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparisonApplication.class, args);
	}
}
