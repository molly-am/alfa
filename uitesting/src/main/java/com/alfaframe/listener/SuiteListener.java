package com.alfaframe.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class SuiteListener implements ISuiteListener, ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuiteListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        LOGGER.info("================================== TEST "
                + result.getName()
                + " STARTED ==================================");
    }
}
