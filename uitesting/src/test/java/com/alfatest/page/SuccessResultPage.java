package com.alfatest.page;

import com.alfaframe.DriverManager;
import com.alfaframe.element.MobileElement;
import com.alfaframe.page.Page;
import com.alfaframe.page.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class SuccessResultPage extends Page {

    @AndroidFindBy(xpath = "//*[@text='Вход в Alfa-Test выполнен']")
    private MobileElement successPageTitle;

    public SuccessResultPage() {
        PageFactory.initElements(DriverManager.getMobileDriver(), this);
    }

    public boolean isPageOpened(){
        return successPageTitle.isElementPresent(30);
    }
}
