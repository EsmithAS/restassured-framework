package com.bdd.Utilidades;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.stereotype.Component;

@CucumberContextConfiguration
@Component
public class Utils {
//    @Autowired
//    Environment env;
//
//    @Value("${app.desa.ux-reqres}")
//    String baseApi;

    public String getBaseApi() {
        System.out.println("baseApi");
        return "";
    }
}
