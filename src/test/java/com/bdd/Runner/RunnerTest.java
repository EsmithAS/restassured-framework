package com.bdd.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/build/cucumber.json",
        features = "src/test/resources/features",
        stepNotifications = true,
        glue = "com.bdd.StepDefinition",
        tags = "@LIST_USER"
)
public class RunnerTest {
    @BeforeClass
    public static void beforeScenario() {
        System.out.println("Antes de la ejecución");
    }

    @AfterClass
    public static void afterScenario() {
        System.out.println("Despues de la ejecución");
    }
}
