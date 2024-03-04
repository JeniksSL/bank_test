package com.bankoperations.test;


import com.bankoperations.test.core.RestAssuredBaseTest;
import com.bankoperations.test.dto.core.ContactType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static com.bankoperations.test.controller.core.ControllerConst.API;
import static org.hamcrest.Matchers.*;


@Sql(scripts = "/scripts/test_data_clear.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionTest extends RestAssuredBaseTest {

    private final BigDecimal firstInitBalance = new BigDecimal("100.0555");
    private final BigDecimal secondInitBalance = new BigDecimal("29.001");
    private final BigDecimal validTransferAmount = new BigDecimal("50.301");
    private final BigDecimal negativeTransferAmount = new BigDecimal("-50.301");
    private final BigDecimal invalidTransferAmount = new BigDecimal("150.301");

    @BeforeEach
    void setUp() {
        RestAssured.baseURI =super.getHost();
    }

    @AfterEach
    void tearDown(){

    }


    @Test
    void Create_two_accounts_then_transfer_money_from_first_to_second() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"login":"%s",
                                "password": "%s",
                                "initialDeposit": "%s",
                                "contacts" :[
                                {
                                 "contact":"%s",
                                   "type":"%s"
                                },
                                {
                                "contact":"%s",
                                   "type":"%s"
                                }]
                                }
                                """,
                        "testLogin",
                        "test_pwd",
                        firstInitBalance,
                        "+123456789",
                        ContactType.PHONE.name(),
                        "test@email.com",
                        ContactType.EMAIL.name()
                ))
                .when()
                .post(API+"/registration/account")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
        Long secondAccountId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"login":"%s",
                                "password": "%s",
                                "initialDeposit": "%s",
                                "contacts" :[
                                {
                                 "contact":"%s",
                                   "type":"%s"
                                },
                                {
                                "contact":"%s",
                                   "type":"%s"
                                }]
                                }
                                """,
                        "testLogin2",
                        "test_pwd2",
                        secondInitBalance,
                        "+1234567890",
                        ContactType.PHONE.name(),
                        "test@email2.com",
                        ContactType.EMAIL.name()
                ))
                .when()
                .post(API+"/registration/account")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract().body().jsonPath().getLong("id");
        String accessToken = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"username":"%s",
                                "password": "%s"}
                                """,
                        "testLogin",
                        "test_pwd"

                ))
                .when()
                .post(API+"/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getString("accessToken");
        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"acceptorAccountId":"%d",
                                "amount": "%s"}
                                """,
                        secondAccountId,
                        validTransferAmount

                ))
                .when()
                .post(API+"/transaction")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("balance", hasToString(firstInitBalance.subtract(validTransferAmount).toString()));
        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"phone":"%s",
                                "page": "%d",
                                "size":"%d"
                                }
                                """,
                        "+1234567890",
                        0,
                        5

                ))
                .when()
                .get(API+"/account")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("objects[0].id", hasToString(secondAccountId.toString()))
                .body("objects[0].balance", hasToString(secondInitBalance.add(validTransferAmount).toString()));
    }

    @Test
    void Create_two_accounts_then_try_to_transfer_invalid_values_and_receive_error() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"login":"%s",
                                "password": "%s",
                                "initialDeposit": "%s",
                                "contacts" :[
                                {
                                 "contact":"%s",
                                   "type":"%s"
                                },
                                {
                                "contact":"%s",
                                   "type":"%s"
                                }]
                                }
                                """,
                        "testLogin",
                        "test_pwd",
                        "100.002",
                        "+123456789",
                        ContactType.PHONE.name(),
                        "test@email.com",
                        ContactType.EMAIL.name()
                ))
                .when()
                .post(API+"/registration/account")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
        Long secondAccountId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"login":"%s",
                                "password": "%s",
                                "initialDeposit": "%s",
                                "contacts" :[
                                {
                                 "contact":"%s",
                                   "type":"%s"
                                },
                                {
                                "contact":"%s",
                                   "type":"%s"
                                }]
                                }
                                """,
                        "testLogin2",
                        "test_pwd2",
                        "100.002",
                        "+1234567890",
                        ContactType.PHONE.name(),
                        "test@email2.com",
                        ContactType.EMAIL.name()
                ))
                .when()
                .post(API+"/registration/account")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract().body().jsonPath().getLong("id");
        String accessToken = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"username":"%s",
                                "password": "%s"}
                                """,
                        "testLogin",
                        "test_pwd"

                ))
                .when()
                .post(API+"/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getString("accessToken");
        //Try to transfer negative value and receive error
         RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"acceptorAccountId":"%d",
                                "amount": "%s"}
                                """,
                        secondAccountId,
                        negativeTransferAmount

                ))
                .when()
                .post(API+"/transaction")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
        //Try to transfer more money than on balance and receive error
        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(String.format(
                        """
                                {"acceptorAccountId":"%d",
                                "amount": "%s"}
                                """,
                        secondAccountId,
                        invalidTransferAmount

                ))
                .when()
                .post(API+"/transaction")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract().body().jsonPath().prettyPrint();
    }
}