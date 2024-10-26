package com.alfaframe.element;

import com.alfaframe.GlobalDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PlatformElement extends Element{

    private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(PlatformElement.class));

    private By notFormatedBy;
    protected GlobalDriver driver;
    protected By foundBy;
    private String nameForLogger="";
    protected String name="";
    private String formatLocator;

    public PlatformElement(GlobalDriver driver, By foundBy) {
        this.foundBy = foundBy;
        this.driver = driver;
    }

    public PlatformElement (GlobalDriver driver){
        this.driver = driver;
    }


    public PlatformElement format(Object... args) {
        nameForLogger = new StringBuilder(name).append(": ").append(Arrays.toString(args)).append("").toString();
        Class<?> byClass = notFormatedBy.getClass();
        try {
            String locatorString = FindByHelper.getStringLocator(notFormatedBy);
            foundBy = (By) byClass.getConstructor(String.class).newInstance(String.format(locatorString, args));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Failed to format locator", e);
        }
        return this;
    }

    public void click() {
        LOGGER.info("Click element : " + foundBy);
        driver.findElement(foundBy).click();
    }

    public String getText(){
        LOGGER.info("Take text value from : " + foundBy);
        return driver.findElement(foundBy).getText();
    }

    public void type(String text){
        LOGGER.info("Type text : " + text + " for locator " + foundBy);
        driver.findElement(foundBy).clear();
        driver.findElement(foundBy).sendKeys(text);
    }

    public String getAttribute(String attr){
        LOGGER.info("Get attribute value with key : [" + attr + "] for locator " + foundBy);
        return driver.findElement(foundBy).getAttribute(attr);
    }

    public boolean isElementPresent(long timeoutSeconds) {
        LOGGER.info("Waiting for element '" + foundBy + "' exists during " + timeoutSeconds + "sec timeout ...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(foundBy));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
