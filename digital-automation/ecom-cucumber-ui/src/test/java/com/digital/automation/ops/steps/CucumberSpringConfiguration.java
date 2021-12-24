package com.digital.automation.ops.steps;

import com.digital.automation.ops.config.Config;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Config.class)
@CucumberContextConfiguration
public class CucumberSpringConfiguration {
}
