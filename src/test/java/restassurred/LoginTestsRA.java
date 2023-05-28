package restassurred;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTestsRA {

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginSuccess() {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("marym@gmail.com")
                .password("Mm12345@")
                .build();

        AuthResponseDTO responseDTO = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AuthResponseDTO.class);

        System.out.println(responseDTO.getToken());

        //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyeW1AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODU4NTc2NjIsImlhdCI6MTY4NTI1NzY2Mn0.m86JHrD4RS-mUtNZnooT_I1Mn6PP6m0E3j56w82glrQ

    }

    @Test
    public void loginWrongEmail() {

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("marymgmail.com").password("Mm12345@")
                .build();

        ErrorDTO errorDTO = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDTO.class);

        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
        System.out.println(errorDTO.getMessage());

    }
    @Test
    public void loginWrongEmailFormat() {

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("marymgmail.com").password("Mm12345@")
                .build();

         given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("Login or Password incorrect"))
                .assertThat().body("path",equalTo("/v1/user/login/usernamepassword"));
    }
}

