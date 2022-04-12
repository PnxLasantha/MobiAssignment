package FunctionalBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class ApiFunctionalBase {
    /**
     *
     * @param url
     * @param contentType
     * @return
     */
    Response res;
    public Response doGetRequest(String url){
       res = RestAssured.given().contentType(ContentType.JSON).get(url);


        return res;
    }

    /**
     *
     * @param url
     * @param contentType
     * @param header
     * @param body
     * @return
     */
    public Response doPostRequest(String url , ContentType contentType,Header header, String body){
        return  null;
    }
}
