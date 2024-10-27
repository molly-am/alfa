package com.alfatest.page;

import com.alfaframe.DriverManager;
import com.alfaframe.element.MobileElement;
import com.alfaframe.page.Page;
import com.alfaframe.page.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;

public class LoginPage extends Page {

    @AndroidFindBy(xpath = "//*[contains(@resource-id, 'tvTitle')]")
    @iOSXCUITFindBy(xpath = "")
    private MobileElement title;

    @AndroidFindBy(id = "etUsername")
    @iOSXCUITFindBy(id = "")
    private MobileElement loginField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*etPassword\")")
    @iOSXCUITFindBy(id = "")
    private MobileElement passwordField;

    @AndroidFindBy(className = "android.widget.ImageButton")
    @iOSXCUITFindBy(id = "")
    private MobileElement showPasswordButton;

    @AndroidFindBy(id = "btnConfirm")
    private MobileElement loginButton;

    @AndroidFindBy(id = "tvError")
    private MobileElement invalidUserDataError;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ProgressBar\")")
    private MobileElement progressBar;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage() {
        PageFactory.initElements(DriverManager.getMobileDriver(true), this);
    }

    @Step("Login page was opened!")
    public boolean isOpened(long timeout){
        LOGGER.info("Check page is opened...");
        return title.isElementPresent(timeout);
    }

    @Step("Get title value")
    public String getTitle(){
        LOGGER.info("Get title value...");
        attachAllureScreenshot();
        return title.getText();
    }

    @Step("Type password : {value}")
    public void typePassword(String value, boolean useKeyboard){
        passwordField.click();
        if (useKeyboard) {
            LOGGER.info("Type " + value + " via keyboard ...");
            passwordField.typeViaKeyboard(value);
        } else {
            LOGGER.info("Paste " + value + "...");
            passwordField.type(value);
        }
        attachAllureScreenshot();
    }

    @Step("Paste 'Login' value: {value}")
    public void typeLogin(String value, boolean useKeyboard){
        if (useKeyboard) {
            LOGGER.info("Type " + value + " via keyboard ...");
            loginField.typeViaKeyboard(value);
        } else {
            LOGGER.info("Paste" + value + " ...");
            loginField.type(value);
        }
        attachAllureScreenshot();
    }

    @Step("Get 'Login' field value")
    public String getLoginText(){
        attachAllureScreenshot();
        return loginField.getText();
    }

    @Step("Get password field value")
    public String getPasswordFieldText(){
        attachAllureScreenshot();
        return passwordField.getText();
    }

    @Step("Check is 'Password' field masked")
    public boolean isPasswordMasked(){
        attachAllureScreenshot();
        return Boolean.parseBoolean(passwordField.getAttribute("password"));
    }

    @Step("Click 'Show button'")
    public void clickShowPasswordButton(){
        attachAllureScreenshot();
        showPasswordButton.click();
    }

    @Step("Check is 'Show button' enabled")
    public boolean isShowPasswordButtonEnabled(){
        attachAllureScreenshot();
        return Boolean.parseBoolean(showPasswordButton.getAttribute("checked"));
    }

    @Step("Click 'Login' button")
    public void clickLoginButton(){
        attachAllureScreenshot();
        loginButton.click();
    }

    @Step("Verify user sees error message")
    public String getErrorMessage(long timeout){
        progressBar.waitForPageLoad(5);
        attachAllureScreenshot();
        return invalidUserDataError.getText();
    }

    @Step("Get error message")
    public boolean isErrorMessagePresent(long timeout){
        progressBar.waitForPageLoad(5);
        attachAllureScreenshot();
        return invalidUserDataError.isElementPresent(timeout);
    }
}
