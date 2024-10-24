package com.alfatest;


import com.alfaframe.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class AlfaBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlfaBaseTest.class);

    @BeforeSuite(alwaysRun = true, description = "Suite Level Setup")
    public void suiteSetUp() {
        LOGGER.info("Start suite");
    }

    @AfterSuite(alwaysRun = true, description = "Suite Level Tear Down")
    public void suiteTearDown(){
        LOGGER.info("Close all drivers");
        DriverManager.closeAllOpenedBrowsers();
    }
}
