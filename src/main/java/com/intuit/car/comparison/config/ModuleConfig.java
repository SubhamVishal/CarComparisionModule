package com.intuit.car.comparison.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@lombok.Data
@Configuration
@ConfigurationProperties(prefix = "carComparisonModule")
public class ModuleConfig {
    private int carClusterSize = 10;
    private int suggestionSize = 10;
}
