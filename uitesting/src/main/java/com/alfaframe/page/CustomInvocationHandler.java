package com.alfaframe.page;

import com.alfaframe.GlobalDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.alfaframe.element.PlatformElement;

public class CustomInvocationHandler implements InvocationHandler {

    private final By locator;
    private final Class<?> clazz;
    private GlobalDriver driver;

    public CustomInvocationHandler(GlobalDriver driver, By locator, Class<?> clazz) {
        this.locator = locator;
        this.clazz = clazz;
        this.driver = driver;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<WebElement> elements = driver.getWebDriver().findElements(locator);
        List<PlatformElement> customs = new ArrayList<>();
        for (int el = 0; el < elements.size(); el++) {
            Constructor<?> fieldConstructor = clazz.getConstructor(GlobalDriver.class, By.class);
            customs.add((PlatformElement) fieldConstructor.newInstance(driver, locator));
        }
        try {
            return method.invoke(customs, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
