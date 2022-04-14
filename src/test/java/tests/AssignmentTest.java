package tests;

import base.ApiBase;
import functionalbase.ApiFunctionalBase;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AssignmentTest extends ApiBase {
    ApiFunctionalBase apiBase = new ApiFunctionalBase();
    Response res;
    String url;
    Integer userID;
    JsonPath jpath;
    List<Integer> resultList, photoID;
    AllureLifecycle lifecycle = Allure.getLifecycle();


    @Test(testName = "Get user details")
    @Description("Get User ID") //this is used in allure reports test description
    public void getUserDataTest() {
        //setting up different test name for allure report
        lifecycle.updateTestCase(testResult -> testResult.setName("Get user details"));

        url = baseUrl + "/users";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();

        //fetching user id of Delphine
        userID = jpath.param("userName", userName).get("find{it.username == userName}.id");
        Assert.assertEquals(res.getStatusCode(), 200, "Users route is failing");
        Assert.assertNotNull(userID, userName + " not found");

    }

    @Test(dependsOnMethods = {"getUserDataTest"}, testName = "Get user posts by user id")
    @Description("Get all the post from thee user Delphine")
    public void getPostsCreatedByUserTest() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Get user posts by user id"));

        url = baseUrl + "/posts";
        res = apiBase.doGetRequest(url);
        Assert.assertEquals(res.getStatusCode(), 200, "Posts route is failing");
        jpath = res.jsonPath();
        resultList = jpath.param("userID", userID).get("findAll{it.userId == userID}.id");
        Assert.assertFalse(resultList.isEmpty(), "User doesn't created any posts");

    }

    @Test(testName = "Validate email address", dependsOnMethods = {"getPostsCreatedByUserTest"})
    @Description("Validate email address")
    public void getPostedCommentsAndValidateEmailTest() {
        List<String> email;
        lifecycle.updateTestCase(testResult -> testResult.setName("Validate comments and email"));

        url = baseUrl + "/comments";
        res = apiBase.doGetRequest(url);
        Assert.assertEquals(res.getStatusCode(), 200, "Comments route is failing");
        jpath = res.jsonPath();
        for (int s : resultList) {
            email = jpath.param("post", s).get("findAll{it.postId == post}.email");
            //validate email though Apache commons library. Using parallelStream to execute assertions faster
            email.parallelStream().forEach(e -> Assert.assertTrue(EmailValidator.getInstance().isValid(e), e + " is an invalid Email"));
        }

    }

    @Test(testName = "Verify user created photo albums", dependsOnMethods = {"getUserDataTest"})
    @Description("Verify user created photo albums")
    public void getPhotoAlbumDetailsTest() {

        lifecycle.updateTestCase(testResult -> testResult.setName("Verify user created photo albums"));

        url = baseUrl + "/albums?userId=" + userID;
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        Assert.assertEquals(res.getStatusCode(), 200, "Albums route is failing");
        resultList = jpath.get("id");
        Assert.assertFalse(resultList.isEmpty(), "User doesn't created any photo albums");


    }

    @Test(testName = "Verify albums contains at least one photo", dependsOnMethods = {"getPhotoAlbumDetailsTest"})
    @Description("Verify albums contains at least one photo")
    public void getUseUploadedPhotos() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Verify albums contains at least one photo"));

        url = baseUrl + "/photos";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        Assert.assertEquals(res.getStatusCode(), 200);
        for (int albumID : resultList) {
            photoID = jpath.param("albumID", albumID).get("findAll{it.albumId == albumID}.id");

            Assert.assertFalse(photoID.isEmpty(), "User doesn't uploaded any photos");
        }


    }

    @Test(description = "Verify Broken photo urls", dependsOnMethods = {"getUseUploadedPhotos",}, testName = "Verify Broken photo urls")
    @Description("Verify Broken photo urls")
    public void verifyBrokenPhotolLinks() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Verify broken photo urls "));
        List<String> imageUrl = new ArrayList<>();
        url = baseUrl + "/photos";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();

        //adding all the image urls to the imageUrl list
        for (int picID : photoID) {
            imageUrl.add(jpath.param("picID", picID).getString("find{it.id == picID}.url"));
        }

        imageUrl.parallelStream().forEach(e -> Assert.assertEquals(apiBase.doGetRequest(e).getStatusCode(), 200, "broken url " + e));

    }


}
