package com.alfaframe;

import org.openqa.selenium.WebDriver;

public abstract class PlatformFactory {

    public abstract WebDriver createDriver();
}
