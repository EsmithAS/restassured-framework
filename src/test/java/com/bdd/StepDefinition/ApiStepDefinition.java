package com.bdd.StepDefinition;

import com.bdd.Step.ApiStep;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import java.io.IOException;

public class ApiStepDefinition {
    private ApiStep apiStep = new ApiStep();

    @Dado("que configuro los headers")
    public void queConfiguroLasCabeceras(DataTable dataTable) {
        apiStep.queConfiguroLasCabeceras(dataTable);
    }

    @Y("configuro los query params")
    public void configuroLosQueryParams(DataTable dataTable) {
        apiStep.configuroLosQueryParams(dataTable);
    }

    @Y("configuro los paths variables")
    public void configuroLosPathsVariables(DataTable dataTable) {
        apiStep.configuroLosPathsVariables(dataTable);
    }

    @Y("configuro el body request {string}")
    public void configuroElBodyRequest(String path, DataTable dataTable) {
        apiStep.configuroElBodyRequest(path, dataTable);
    }

    @Cuando("ejecuto el api")
    public void ejecutoElApi(DataTable dataTable) throws IOException {
        apiStep.ejecutoElApi(dataTable);
    }

    @Entonces("valido que el status code sea {string}")
    public void validoQueElStatusCodeSea(String code) {
        apiStep.getStatusCodeSea(code);
    }

    @Y("verifico la respuesta del servicio")
    public void verificoLaRespuestaDelServicio(DataTable dataTable) {
        apiStep.verificoLaRespuestaDelServicio(dataTable);
    }

    @Y("valido el esquema de la respuesta {string}")
    public void validoElEsquemaDeLaRespuesta(String path) {
        apiStep.validoElEsquemaDeLaRespuesta(path);
    }

    @Cuando("ejecuto el api y configuro el body desde un excel {string}")
    public void ejecutoElApiYConfiguroElBodyDesdeUnExcel(String path, DataTable dataTable) throws IOException {
        apiStep.ejecutoElApiYConfiguroElBodyDesdeUnExcel(path, dataTable);
    }

    @Entonces("valido que el status code de las peticiones sea {string}")
    public void validoQueElStatusCodeDeLasPeticionesSea(String statusCode) {
        apiStep.validoQueElStatusCodeDeLasPeticionesSea(statusCode);
    }
}
