package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.mysql.Request;
import ru.netology.web.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class LogInTest {

    @BeforeEach
    void shouldOpen() {
        open( "http://localhost:9999");
    }

    @AfterAll
    static void shouldClearAll() throws SQLException {
       val request = new Request();
       request.shouldDelete();
    }

    @Test
    void shouldLogIn() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val codeInfo = Request.getCode();
        val dashboardPage = verificationPage.validVerify(codeInfo);
        dashboardPage.assertDashboardPage();
    }
}
