package Tests;

import FunctionalBase.ApiFunctionalBase;
import base.ApiBase;
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
        resultList = jpath.param("userID", userID).get("findAll{it.userId == userID}.id");
        Assert.assertFalse(resultList.isEmpty());

    }

    @Test(dependsOnMethods = {"getPostsCreatedByUserTest"})
    @Description("Validate email address")
    public void getPostedCommentsAndValidateEmailTest() {
        List<String> email;
        lifecycle.updateTestCase(testResult -> testResult.setName("Validate comments and email"));

        url = baseUrl + "/comments";
        res = apiBase.doGetRequest(url);
        Assert.assertEquals(res.getStatusCode(), 200);
        jpath = res.jsonPath();
        for (int s : resultList) {
            email = jpath.param("post", s).get("findAll{it.postId == post}.email");
            for (String mail : email) {
                //using apache common validator for validate emails
                Assert.assertTrue(EmailValidator.getInstance().isValid(mail));
            }

        }

    }

    @Test(description = "Verify user created photo albums", dependsOnMethods = {"getUserDataTest"})
    public void getPhotoAlbumDetailsTest() {

        lifecycle.updateTestCase(testResult -> testResult.setName("Verify user created photo albums"));

        url = baseUrl + "/albums?userId=" + userID;
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        Assert.assertEquals(res.getStatusCode(), 200);
        resultList = jpath.get("id");
        Assert.assertFalse(resultList.isEmpty());


    }

    @Test(description = "Verify albums contains atleast one photo", dependsOnMethods = {"getPhotoAlbumDetailsTest"})
    public void getUseUploadedPhotos() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Verify albums contains atleast one photo"));

        url = baseUrl + "/photos";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        Assert.assertEquals(res.getStatusCode(), 200);
        for (int albumID : resultList) {
            photoID = jpath.param("albumID", albumID).get("findAll{it.albumId == albumID}.id");

            Assert.assertFalse(photoID.isEmpty());
        }


    }

    @Test(description = "Verfiy Broken  urls", dependsOnMethods = {"getUseUploadedPhotos",})
    public void verifyBrokenThumbnailLinks() {
        lifecycle.updateTestCase(testResult -> testResult.setName("Verify broken urls "));
        List<String> imageUrl = new ArrayList<>();
        url = baseUrl + "/photos";
        res = apiBase.doGetRequest(url);
        jpath = res.jsonPath();
        System.out.println(photoID);
        for (int picID : photoID) {
            imageUrl.add(jpath.param("picID", picID).getString("find{it.id == picID}.url"));
        }
        imageUrl.parallelStream().forEach(e -> Assert.assertEquals(apiBase.doGetRequest(e).getStatusCode(), 200));

    }


    //validate for title and body


}
