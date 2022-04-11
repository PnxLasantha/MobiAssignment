package FunctionalBase;

import io.restassured.http.Header;
import io.restassured.response.Response;

public class ApiFunctionalBase {


    protected Response doGetRequest(String url){

        return null;
    }

    protected Response doPostRequest(String url , Header header, String body){
        return  null;
    }
}
