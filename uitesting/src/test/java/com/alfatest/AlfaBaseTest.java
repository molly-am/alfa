package com.alfatest;

import com.alfaframe.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class AlfaBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlfaBaseTest.class);

    @BeforeSuite(alwaysRun = true, description = "Suite Level Setup")
    public void suiteSetUp() {
        LOGGER.info("Start suite");
    }

    @AfterMethod(alwaysRun = true, description = "Test tear down")
    public void afterMethodSetup(){
        LOGGER.info("Close opened driver after test");
        DriverManager.closeDriver(DriverManager.getMobileDriver());
    }

    @AfterSuite(alwaysRun = true, description = "Suite Level Tear Down")
    public void suiteTearDown(){
        LOGGER.info("Close all drivers");
        DriverManager.closeAllOpenedBrowsers();
    }
}
