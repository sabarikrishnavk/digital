package com.digital.automation.ops;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"classpath:features/"},
    plugin = {"pretty"},
    glue = {"com.digital.automation.ops.steps"})
public class RunCucumberIT {
}
