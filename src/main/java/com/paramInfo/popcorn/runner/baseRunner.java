package com.paramInfo.popcorn.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.paramInfo.popcorn.stepDef",
        monochrome = true,
        plugin = {"pretty","html:target/cucumber","json:target/cucumber-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)


public class baseRunner {
}
