package restassurred;

import com.jayway.restassured.RestAssured;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.GetAllContactsDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTestsRA {
    String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyeW1AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODU4NTc2NjIsImlhdCI6MTY4NTI1NzY2Mn0.m86JHrD4RS-mUtNZnooT_I1Mn6PP6m0E3j56w82glrQ";
    @BeforeMethod
    public void  preCondition(){
        RestAssured.baseURI="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath="v1";

    }

    @Test
    public void getAllContactsSuccess(){

        GetAllContactsDTO contactsDTO =given()
                .header("Authorization",token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(GetAllContactsDTO.class);


        List<ContactDTO> list =contactsDTO.getContacts();
        for (ContactDTO contact:list) {
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("Size of list -->" + list.size());

        }

    }
    @Test
    public void getAllContactWrongToken(){
        ErrorDTO errorDTO = given()
                .header("Authorization","mbmbmbmbmbmb")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDTO.class);

        Assert.assertEquals(errorDTO.getError(),"Unauthorized");
        System.out.println(errorDTO.getError());

    }
}