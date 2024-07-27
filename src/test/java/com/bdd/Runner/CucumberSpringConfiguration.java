package com.bdd.Runner;

import com.restassured.restassured.RestassuredApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@CucumberContextConfiguration
@SpringBootTest(classes = RestassuredApplication.class)
public class CucumberSpringConfiguration {
}
