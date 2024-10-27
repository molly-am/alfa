package com.alfaframe.element;

import com.alfaframe.GlobalDriver;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import io.appium.java_client.AppiumBy;

public class MobileElement extends Element {

    private By notFormatedBy;
    protected GlobalDriver driver;
    protected By foundBy;
    private String nameForLogger = "";
    protected String name = "";
    private String formatLocator;

    public MobileElement(GlobalDriver driver, By foundBy) {
        super(driver, foundBy);
    }

    @Override
    public MobileElement format(Object... args) {
        By by = null;
        String locator = this.foundBy.toString();
        if (locator.startsWith("By.xpath: ")){
            locator = String.format(StringUtils.remove(locator, "By.xpath: "), args);
            by = By.xpath(locator);
        }
        if (locator.startsWith("By.AndroidUIAutomator:")){
            locator = String.format(StringUtils.remove(locator, "By.AndroidUIAutomator:"), args);
            by = AppiumBy.androidUIAutomator(locator);
        }
        if (locator.startsWith("By.AccessibilityId: ")){
            locator = String.format(StringUtils.remove(locator, "By.AccessibilityId: "), args);
            by = AppiumBy.accessibilityId(locator);
        }
        return new MobileElement(driver, by);
    }

}
