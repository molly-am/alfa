package com.alfatest.test;


import com.alfaframe.listener.SuiteListener;
import com.alfatest.AlfaBaseTest;
import com.alfatest.page.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Owner;

@Listeners({SuiteListener.class})
public class LoginTest extends AlfaBaseTest {


    @Test(description = "Check title")
    @Owner("ykhudaleyeu")
    public void titleVisibilityTest(){
        LoginPage loginPage = new LoginPage();
//        boolean isDisplayed = loginPage.isElementPresent();
//        Assert.assertTrue(isDisplayed, "App wasn't opened");
        Assert.assertEquals(loginPage.getTitle(), "Вход в Alfa-Test", "Title is not valid");
        System.out.println();
    }

    @Test(description = "Check password fiels")
    @Owner("ykhudaleyeu")
    public void checkPasswordField(){
        LoginPage loginPage = new LoginPage();
        String initText = loginPage.getPasswordFieldText();
        Assert.assertEquals(initText, "Пароль", "Pasword init text is invalid");
        loginPage.type("Password");
        Assert.assertNotEquals(initText, "Password", "Pasword init text is invalid");
        boolean isMasked = loginPage.isPasswordMasked();
        loginPage.clickShowPasswordButton();
        Assert.assertNotEquals(isMasked, loginPage.isPasswordMasked(), "Password hint mask is not valid!");
    }
}
