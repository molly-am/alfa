package com.alfaframe;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class AndroidFactory extends PlatformFactory{

    private static Logger LOGGER = LoggerFactory.getLogger(AndroidFactory.class);

    @Override
    public WebDriver createDriver() {
        DesiredCapabilities desiredCapabilities = GlobalConfig.getInstance().getDesiredCapabilities();
        LOGGER.info(desiredCapabilities.toString());
        String host = "http://127.0.0.1:4723/";
        AppiumDriver driver = null;
        try {
            driver = new AndroidDriver(new URL(host), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        LOGGER.info("Applied android caps :\n" + "====================================================================================");
        desiredCapabilities.asMap().entrySet().stream().forEach(it -> LOGGER.info(it.getKey() + "=" + it.getValue()));
        LOGGER.info("===============================================================\n");
        return driver;
    }
}
