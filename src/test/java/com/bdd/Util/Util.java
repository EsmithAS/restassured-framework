package com.bdd.Util;

import com.bdd.Constant.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Util {
    public static String getValueFromDataTable(DataTable dataTable, String key) {
        List<Map<String, String>> mapList = dataTable.asMaps();
        return (String) mapList.get(0).get(key);
    }

    public static Headers configHeaders(DataTable dataTable) {
        List<Header> headerList = new LinkedList<>();
        List<Map<String, String>> headerMapList = dataTable.asMaps();

        headerMapList.forEach(map -> {
            String name = map.get("header");
            String value = map.get("value");
            Header header = new Header(name, value);
            headerList.add(header);
        });

        Headers headers = new Headers(headerList);
        return headers;
    }

    public static String getProperty(String property) throws IOException {
        String appConfigPath = Project.USER_DIR.concat("\\app.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        String valueProperty = properties.getProperty(property, "default");
        return valueProperty;
    }

    public static String getEnvironment(String property) throws IOException {
        String env = Util.getProperty("env");
        Yaml yaml = new Yaml();

        try (InputStream in = Files.newInputStream(Paths.get(Project.USER_DIR.concat("\\src\\test\\resources\\config\\environment.yml")))) {
            Map<String, Object> data = yaml.load(in);
            Map<String, Object> app = (Map<String, Object>) data.get("app");
            Map<String, Object> environments = (Map<String, Object>) app.get(env);
            String val = "";

            if (app.get(env) == null) {
                environments = (Map<String, Object>) app.get("default");
                property = "default";
            }

            for (Map.Entry<String, Object> entry : environments.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals(property)) {
                    val = (String) value;
                    break;
                }
            }

            return val;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isJSONValid(String jsonString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
