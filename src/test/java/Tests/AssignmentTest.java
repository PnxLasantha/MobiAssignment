package Tests;

import FunctionalBase.ApiFunctionalBase;
import base.ApiBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AssignmentTest extends ApiBase {
    ApiFunctionalBase apiBase = new ApiFunctionalBase();
    ContentType contentType;
    Response res;
    String url;

    @Test
    public void getUserData() {
        url = baseUrl + "/users";
        res = apiBase.doGetRequest(url);

        System.out.println(res.getStatusCode());





    }
}
