package com.digital.automation.ops.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@ComponentScan(basePackages = {"com.digital.automation.ops"})
public class Config {
}
