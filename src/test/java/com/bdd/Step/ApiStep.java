package com.bdd.Step;

import com.bdd.Constant.Project;
import com.bdd.Util.Util;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        try {
            List<Map<String, String>> mapList = dataTable.asMaps();
            String pathFile = Project.USER_DIR + "/src/test/resources/request/json/" + path;
            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            body = bufferedReader.lines().collect(Collectors.joining());
            JsonPath jsonPath = new JsonPath(body);

            mapList.forEach(map -> {
                String nodo = map.get("key");
                String valor = map.get("value");

                String valueVariablePath = String.valueOf(jsonPath.getString(nodo)).trim();
                body = body.replace(valueVariablePath, valor);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Logger.getGlobal().log(Level.INFO, "BODY REQUEST: {0}", body);
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
        String responseString = response.getBody().asString();
        List<Map<String, String>> mapList = dataTable.asMaps();

        mapList.forEach(map -> {
            if (Util.isJSONValid(responseString)) {
                String nodo = map.get("node");
                String valorEsperado = map.get("value");
                JsonPath jsonPath = new JsonPath(response.getBody().asString());

                String valorObtenido = String.valueOf(jsonPath.getString(nodo)).trim();
                Logger.getGlobal().log(Level.INFO, "VALOR RESPONSE: {0}", valorObtenido);
                Assert.assertEquals(valorEsperado, valorObtenido);
            } else {
                Assert.fail("Respuesta de la api no tiene formato correcto");
            }
        });
    }

    public void validoElEsquemaDeLaRespuesta(String path) {
        File schema = new File(Project.USER_DIR + "/src/test/resources/schemas/" + path);
        Logger.getGlobal().log(Level.INFO, "BODY REQUEST: {0}", response.getBody().asString());
        Assert.assertThat(response.getBody().asString(), JsonSchemaValidator.matchesJsonSchema(schema));
    }
}
