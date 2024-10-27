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

import com.alfaframe.element.Element;

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
        List<Element> customs = new ArrayList<>();
        for (int element = 0; element < elements.size(); element++) {
            Constructor<?> fieldConstructor = clazz.getConstructor(GlobalDriver.class, By.class);
            customs.add((Element) fieldConstructor.newInstance(driver, locator));
        }
        try {
            return method.invoke(customs, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
