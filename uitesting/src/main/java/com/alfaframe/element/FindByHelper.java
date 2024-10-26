package com.alfaframe.element;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;

import io.appium.java_client.AppiumBy;

public class FindByHelper {

    public static String getLocatorFieldName(By by)
    {
        if(by instanceof By.ById)
        {
            return "id";
        }
        if(by instanceof By.ByLinkText)
        {
            return "linkText";
        }
        if(by instanceof By.ByName)
        {
            return "name";
        }
        if(by instanceof By.ByPartialLinkText)
        {
            return "partialLinkText";
        }
        if(by instanceof By.ByTagName)
        {
            return "tagName";
        }
        if(by instanceof By.ByXPath)
        {
            return "xpathExpression";
        }
        if(by instanceof AppiumBy.ByAndroidUIAutomator){
            return "androidUIAutomator";
        }
        if(by instanceof AppiumBy.ByClassName){
            return "className";
        }
        throw new RuntimeException("Method format is not available for " + by.toString());
    }

    public static String getStringLocator(By by) {
        String fieldName = getLocatorFieldName(by);
        try {
            return (String) FieldUtils.readField(by, fieldName, true);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException("Failed to format locator", e);
        }
    }
}
