package Tests;

import FunctionalBase.ApiFunctionalBase;
import base.ApiBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AssignmentTest extends ApiBase {
    ApiFunctionalBase apiBase = new ApiFunctionalBase();
    ContentType contentType;
    Response res;
    String url;
    Integer userID;
    JsonPath jpath;
    List<Integer> postList;

    @Test
    public void getUserData() {
        url = baseUrl + "/users";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        userID = jpath.param("userName", userName).get("find{it.username == userName}.id");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertNotNull(userID, userName + " not found");

    }

    @Test(dependsOnMethods = {"getUserData"})
    public void getUserPosts() {
        url = baseUrl + "/posts";
        res = apiBase.doGetRequest(url);

        jpath = res.jsonPath();
        postList = jpath.param("userID", userID).get("findAll{it.userId == userID}.id");

        Assert.assertNotNull(postList);

    }

    @Test(dependsOnMethods = {"getUserPosts"})
    public void getComments() {
        url = baseUrl + "/comments";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        for (int s : postList) {
            List<String> email = jpath.param("post", s).get("findAll{it.postId == post}.email");
            for (String mail : email) {
                Assert.assertTrue(EmailValidator.getInstance().isValid(mail));
            }

        }

    }

    //validate for title and body


}
