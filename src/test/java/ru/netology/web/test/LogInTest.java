package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.mysql.Request;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LogInTest {

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void shouldClearAll() {
      Request.shouldDelete();
    }

    @Test
    void shouldLogInSuccessfully() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val codeInfo = Request.getCode();
        val dashboardPage = verificationPage.validVerify(codeInfo);

    }

    @Test
    void shouldBlockIfPasswordIsInvalid() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val otherAuthInfo = DataHelper.getOtherAuthInfo();
        loginPage.nonValidPassword(authInfo, otherAuthInfo);
        loginPage.clearFieldAndPutInvalidPassword(otherAuthInfo);
        loginPage.clearFieldAndPutInvalidPassword(otherAuthInfo);
        loginPage.getErrorMessageAboutBlocking();
    }
}
