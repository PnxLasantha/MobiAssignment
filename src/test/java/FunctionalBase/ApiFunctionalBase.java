package FunctionalBase;

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

    protected Response doGetRequest(String url, ContentType contentType){

        return null;
    }

    /**
     *
     * @param url
     * @param contentType
     * @param header
     * @param body
     * @return
     */
    protected Response doPostRequest(String url , ContentType contentType,Header header, String body){
        return  null;
    }
}
