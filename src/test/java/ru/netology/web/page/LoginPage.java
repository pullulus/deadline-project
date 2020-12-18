package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");
    private SelenideElement errorMessageAboutBlocking = $(withText("Система заблокирована из-за трехкратного неправильного ввода пароля"));

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public LoginPage nonValidPassword(DataHelper.AuthInfo info, DataHelper.AuthInfo other) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(other.getPassword());
        loginButton.click();
        errorMessage.shouldBe(Condition.visible);
        return new LoginPage();
    }

    public LoginPage clearFieldAndPutInvalidPassword(DataHelper.AuthInfo other) {
        passwordField.doubleClick();
        passwordField.setValue(other.getPassword());
        loginButton.click();
        errorMessage.shouldBe(Condition.visible);
        return new LoginPage();
    }

    public void getErrorMessageAboutBlocking() {
        errorMessageAboutBlocking.shouldBe(Condition.visible);
    }
}
