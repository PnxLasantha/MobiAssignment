package Tests;

import FunctionalBase.ApiFunctionalBase;
import base.ApiBase;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Description;
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
    AllureLifecycle lifecycle = Allure.getLifecycle();


    @Test
    @Description("Get User ID")
    public void getUserDataTest() {
        //setting up different name for allure report
        lifecycle.updateTestCase(testResult -> testResult.setName("Get User ID"));

        url = baseUrl + "/users";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();

        //fetching user id of delphine
        userID = jpath.param("userName", userName).get("find{it.username == userName}.id");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertNotNull(userID, userName + " not found");

    }

    @Test(dependsOnMethods = {"getUserDataTest"}, testName = "Get user posts by user id")
    @Description("Get all the post from thee user Delp")
    public void getPostsCreatedByUserTest() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Get user posts by user id"));

        url = baseUrl + "/posts";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        postList = jpath.param("userID", userID).get("findAll{it.userId == userID}.id");
        Assert.assertNotNull(postList);

    }

    @Test(dependsOnMethods = {"getPostsCreatedByUserTest"})
    @Description("Validate email address")
    public void getPostedCommentsAndValidateEmailTest() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Validate comments and email"));

        url = baseUrl + "/comments";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        for (int s : postList) {
            List<String> email = jpath.param("post", s).get("findAll{it.postId == post}.email");
            for (String mail : email) {
                //using apache common validator for validate emails
                Assert.assertTrue(EmailValidator.getInstance().isValid(mail));
            }

        }

    }

    //validate for title and body


}
