package com.bdd.Step;

import com.bdd.Utilidades.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = Utils.class)
public class ApiStep {

    public void queConfiguroLasCabeceras(DataTable dataTable) {

    }

    public void configuroLosQueryParams(DataTable dataTable) {
    }

    public void configuroLosPathsVariables(DataTable dataTable) {
    }

    public void configuroElBodyRequest(String path, DataTable dataTable) {
    }

    public void ejecutoElApi(DataTable dataTable) {
    }

    public void validoQueElStatusCodeSea(String code) {
    }

    public void verificoLaRespuestaDelServicio(DataTable dataTable) {
    }

    public void validoElEsquemaDeLaRespuesta(String path) {
    }
}
