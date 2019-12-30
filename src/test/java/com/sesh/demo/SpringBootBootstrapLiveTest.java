package com.sesh.demo;

import com.sesh.demo.persistence.models.Gift;

import static junit.framework.TestCase.assertTrue;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest(classes = {Application.class}, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:8081/api/gifts";

    @Test
    public void whenGetAllGifts_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetGiftsByName_thenOK() {
        Gift gift = createRandomGift();
        createGiftAsUri(gift);
        Response response = RestAssured.get(
                API_ROOT + "/name/" + gift.getName());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void whenGetCreatedGiftById_thenOK() {
        Gift gift = createRandomGift();
        String location = createGiftAsUri(gift);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        JsonPath path = response.jsonPath();
        assertEquals(gift.getName(), path.get("name"));
    }

    @Test
    public void whenGetNotExistGiftById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }


    @Test
    public void whenCreateNewGift_thenCreated() {
        Gift gift = createRandomGift();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gift)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedGift_thenUpdated() {
        Gift gift = createRandomGift();
        String location = createGiftAsUri(gift);
        gift.setId(Long.parseLong(location.split("api/gifts/")[1]));
        gift.setName("Doll House");
        gift.setRecipient("Little Sister");
        gift.setPrice(14);
        gift.setLocation("Walmart");
        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(gift).put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Doll House", response.jsonPath().get("name"));
        assertEquals("Little Sister", response.jsonPath().get("recipient"));
        assertEquals("14", response.jsonPath().get("price").toString());
        assertEquals("Walmart", response.jsonPath().get("location"));
    }

    @Test
    public void whenInvalidGift_thenError() {
        Gift gift = createRandomGift();
        gift.setName(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gift)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteCreatedGift_thenOk() {
        Gift gift = createRandomGift();
        String location = createGiftAsUri(gift);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }


    private Gift createRandomGift() {

        Gift gift = new Gift();
        gift.setName(randomAlphabetic(10));
        gift.setPrice(Integer.valueOf(randomNumeric(2)));
        gift.setRecipient(randomAlphabetic(15));
        gift.setLocation(randomAlphabetic(15));
        return gift;
    }

    private String createGiftAsUri(Gift gift) {

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gift)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

}
