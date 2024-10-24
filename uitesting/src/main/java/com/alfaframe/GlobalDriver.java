package com.alfaframe;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class GlobalDriver implements WebDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfig.class);

    private WebDriver driver;

    public GlobalDriver(Platform platform) {
        switch (platform) {
            case ANDROID:
                driver = new AndroidFactory().createDriver();
                break;
            case IOS:
                //todo: add ios logic
                break;
            default:
                throw new RuntimeException(platform + " not implemented");
        }
    }

    @Override
    public void get(String url) {

    }

    public WebDriver getWebDriver() {
        return driver;
    }

    @Override
    public String getCurrentUrl() {
        return "";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        LOGGER.info("Find element by " + by);
        return this.driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return "";
    }

    @Override
    public void close() {
        LOGGER.debug("Browser will be closed");
        driver.close();
    }

    @Override
    public void quit() {

    }

    @Override
    public Set<String> getWindowHandles() {
        return Set.of();
    }

    @Override
    public String getWindowHandle() {
        return "";
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }
}
