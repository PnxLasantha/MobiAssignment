package functionalbase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class ApiFunctionalBase {
    /**
     * @param url url of the request
     * @return this will return a Response
     */
    Response res;
    public Response doGetRequest(String url){
       res = RestAssured.given().contentType(ContentType.JSON).get(url);
        return res;
    }

    /**
     * @param url    url of the request
     * @param header header of the request need to user Header type
     * @param body   request body.
     * @return this will return a Response
     */
    public Response doPostRequest(String url, Header header, String body) {
        res = RestAssured.given().header(header).contentType(ContentType.JSON).post(url);
        return res;
    }


}
