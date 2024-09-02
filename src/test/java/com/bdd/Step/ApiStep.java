package com.bdd.Step;

import com.bdd.Util.Util;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiStep {

    private RequestSpecification specification;
    private Response response;
    private String body;

    public void queConfiguroLasCabeceras(DataTable dataTable) {
        Headers headers = Util.configHeaders(dataTable);
        specification = RestAssured.given().log().all().headers(headers);
        Logger.getGlobal().log(Level.INFO, "Headers >> {0}", headers);
    }

    public void configuroLosQueryParams(DataTable dataTable) {
        List<Map<String, String>> queryParams = dataTable.asMaps();
        queryParams.forEach(queryParam -> {
            String namequeryParam = queryParam.get("key");
            String valuequeryParam = queryParam.get("value");
            specification.queryParam(namequeryParam, valuequeryParam);
        });
        Logger.getGlobal().log(Level.INFO, "Query Params >> {0}", queryParams);
    }

    public void configuroLosPathsVariables(DataTable dataTable) {
        List<Map<String, String>> pathsVariable = dataTable.asMaps();
        pathsVariable.forEach(pathVariable -> {
            String namequeryParam = pathVariable.get("key");
            String valuequeryParam = pathVariable.get("value");
            specification.pathParam(namequeryParam, valuequeryParam);
        });
        Logger.getGlobal().log(Level.INFO, "Paths Variables >> {0}", pathsVariable);
    }

    public void configuroElBodyRequest(String path, DataTable dataTable) {
    }

    public void ejecutoElApi(DataTable dataTable) throws IOException {
        String api = Util.getValueFromDataTable(dataTable, "api");
        String endpoint = Util.getValueFromDataTable(dataTable, "endpoint");
        String method = Util.getValueFromDataTable(dataTable, "method");
        String urlApi = Util.getEnvironment(api).concat(endpoint);

        switch (method.toUpperCase()) {
            case "GET":
                response = specification.when().log().all().get(urlApi);
                break;
            case "POST":
                response = specification.body(body).when().log().all().post(urlApi);
                break;
            case "PUT":
                response = specification.body(body).when().log().all().put(urlApi);
                break;
            case "DELETE":
                response = specification.when().log().all().delete(urlApi);
                break;
            default:
                Logger.getGlobal().log(Level.WARNING, "Method not found >> {0}", method);
        }
        response.prettyPeek();
    }

    public int getStatusCodeSea(String code) {
        int statusCode = Integer.parseInt(code);
        response.then().assertThat().statusCode(statusCode);
        return statusCode;
    }

    public void verificoLaRespuestaDelServicio(DataTable dataTable) {
    }

    public void validoElEsquemaDeLaRespuesta(String path) {
    }
}
