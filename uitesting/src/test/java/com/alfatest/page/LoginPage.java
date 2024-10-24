package com.alfatest.page;

import com.alfaframe.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alfaframe.Platform;
import com.alfaframe.element.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import com.alfaframe.page.Page;
import com.alfaframe.page.PageFactory;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

public class LoginPage extends Page {

//    @AndroidFindBy(xpath = "//*[contains(@text, 'Alfa')]")
//    private MobileElement title;

    @AndroidFindBy(id = "tvTitle")
    private MobileElement title;

    @AndroidFindBy(id = "etPassword")
    private MobileElement passwordField;

    @AndroidFindBy(id = "text_input_end_icon")
    private MobileElement showPasswordButton;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage() {
        PageFactory.initElements(DriverManager.getNewDriver(Platform.ANDROID), this);
    }

    @Step("Login page was opened!")
    public boolean isElementPresent(){
        return title.isElementPresent(10);
    }

    @Step("Get title text")
    public String getTitle(){
        attachScreen(makeScreenshot());
        return title.getText();
    }

    @Step("Type text : {text}")
    public void type(String text){
        passwordField.type(text);
    }

    @Step("Get password field value")
    public String getPasswordFieldText(){
        return passwordField.getText();
    }

    @Step("Check is Password masked")
    public boolean isPasswordMasked(){
        attachScreen(makeScreenshot());
        return Boolean.parseBoolean(passwordField.getAttribute("password"));
    }

    @Step("Tap Show button")
    public void clickShowPasswordButton(){
        attachScreen(makeScreenshot());
        showPasswordButton.click();
    }

    @Attachment(type = "image/png")
    public static byte[] attachScreen(File screenshot) {
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
