package com.alfaframe;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DriverManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);

    private static ThreadLocal<GlobalDriver> defaultMobileDriver = new ThreadLocal<GlobalDriver>();
    private static List<GlobalDriver> drivers = new CopyOnWriteArrayList<GlobalDriver>();
    private static ThreadLocal<List<GlobalDriver>> localDriverList = new ThreadLocal<List<GlobalDriver>>();
    private static ThreadLocal<GlobalDriver> defaultDriver = new ThreadLocal<GlobalDriver>();

    public static WebDriver getDriver(){
        String platform = GlobalConfig.getInstance().getPlatformName();
        if (Platform.ANDROID.getPlatform().equals(platform.toLowerCase())) {
            LOGGER.info("Create new android driver");
            return getMobileDriver();
        } else {
            throw new UnsupportedOperationException("There is no driver logic creation for non-android device");
        }
    }

    public static GlobalDriver getMobileDriver(boolean useNewDriver) {
        if (null == defaultMobileDriver.get() || useNewDriver) {
            getNewDriver(Platform.valueOf(GlobalConfig.getInstance().getPlatformName()));
        }
        return defaultMobileDriver.get();
    }

    public static GlobalDriver getMobileDriver(){
        return getMobileDriver(false);
    }

    public static GlobalDriver getNewDriver(Platform platform) {
        LOGGER.debug("Create new instance of Driver.");
        GlobalDriver driver = new GlobalDriver(platform);
        switch (platform) {
            case ANDROID:
            case IOS:
                defaultMobileDriver.set(driver);
                break;
            default:
                setDefaultDriver(driver);
        }
        drivers.add(driver);
        List<GlobalDriver> localList = localDriverList.get() == null ? new ArrayList<>() : localDriverList.get();
        localList.add(driver);
        localDriverList.set(localList);
        return driver;
    }

    public static void setDefaultDriver(GlobalDriver driver) {
        defaultDriver.set(driver);
    }

    public static void closeAllOpenedBrowsers() {
        LOGGER.debug("Close all opened during tests executing browsers");
        int i = 0;
        List<GlobalDriver> driversToClose = new LinkedList<GlobalDriver>();
        try {
            driversToClose.addAll(drivers);
            for (GlobalDriver driver : driversToClose) {
                LOGGER.debug("Close Browser " + i);
                i++;
                closeDriver(driver);
                LOGGER.debug("Success");
            }
        } finally {
            defaultDriver.set(null);
            defaultMobileDriver.set(null);
        }
    }

    public static void closeDriver(GlobalDriver driver) {
        LOGGER.debug("Trying to close browser (driver.quit()): " + driver);
        try {
            if (driver != null) {
                driver.quit();
                drivers.remove(driver);
            }
        } catch (WebDriverException exc) {
            exc.printStackTrace();
        } finally {
            drivers.remove(driver);
        }
    }
}
