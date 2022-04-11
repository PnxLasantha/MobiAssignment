package FunctionalBase;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class ApiFunctionalBase {


    protected Response doGetRequest(String url, ContentType contentType){

        return null;
    }

    protected Response doPostRequest(String url , ContentType contentType,Header header, String body){
        return  null;
    }
}
