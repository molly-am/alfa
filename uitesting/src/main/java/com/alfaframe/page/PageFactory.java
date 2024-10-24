package com.alfaframe.page;

import com.alfaframe.GlobalConfig;
import com.alfaframe.GlobalDriver;

import org.openqa.selenium.By;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.alfaframe.annotation.FindBy;
import com.alfaframe.element.Element;
import com.alfaframe.element.MobileElement;
import com.alfaframe.element.PlatformElement;
import org.apache.commons.lang3.reflect.FieldUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class PageFactory {

    public static <T extends Page> T initElements(GlobalDriver driver, Class<T> pageClass) {
        T page = instantiatePage(driver, pageClass);
        initElements(driver, page);
        return page;
    }

    private static <T extends Page> T instantiatePage(GlobalDriver driver, Class<T> pageClass) {
        try {
            try {
                Constructor<T> constructor = pageClass.getConstructor();
                return constructor.newInstance();
            } catch (NoSuchMethodException e) {
                return pageClass.newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Page> void initElements(GlobalDriver driver, T page) {
        Class<?> pageClass = page.getClass();
        List<Field> annotatedFields = new ArrayList<Field>();
        while(Page.class.isAssignableFrom(pageClass)) {
            for(Field field: pageClass.getDeclaredFields()) {
                if(field.isAnnotationPresent(FindBy.class) || field.isAnnotationPresent(AndroidFindBy.class) || field.isAnnotationPresent(iOSXCUITFindBy.class)) {
                    annotatedFields.add(field);
                }
            }
            pageClass = pageClass.getSuperclass();
        }
        initFields(page, annotatedFields.toArray(new Field[0]), driver);
    }

    private static void initFields(Page page, Field[] fields, GlobalDriver driver ) {
        for (Field field : fields) {
            try {
                Class<?> fieldClass = field.getType();
                if (List.class.isAssignableFrom(fieldClass)) {
                    Type genericType = field.getGenericType();
                    Class<?> listElementClass = (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    if (Element.class.isAssignableFrom(listElementClass)) {
                        FindBy elementAnnotation = field.getAnnotation(FindBy.class);
                        By by = setFindByToElement(elementAnnotation);
                        field.setAccessible(true);
                        field.set(page, createList(driver, page.getClass().getClassLoader(), by, (Class<Element>) listElementClass));

                    }
                }
                switch (GlobalConfig.getInstance().getPlatformName().toLowerCase()) {
                    case "android":
                        if (MobileElement.class.isAssignableFrom(fieldClass)) {
                            AndroidFindBy annotation = field.getAnnotation(AndroidFindBy.class);
                            By by = setAndroidMobileFindByToElement(annotation);
                            Constructor<?> fieldConstructor = fieldClass.getConstructor(GlobalDriver.class, By.class);
                            field.setAccessible(true);
                            field.set(page, fieldConstructor.newInstance(driver, by));
                        }
                        break;
                    default:
                        if (GlobalConfig.class.isAssignableFrom(fieldClass)) {
                            FindBy annotation = field.getAnnotation(FindBy.class);
                            By by = setFindByToElement(annotation);
                            Constructor<?> fieldConstructor = fieldClass.getConstructor(GlobalDriver.class, By.class);
                            field.setAccessible(true);
                            PlatformElement element = (PlatformElement) fieldConstructor.newInstance(driver, by);
                            FieldUtils.writeField(element, "name", field.getName(), true);
                            field.set(page, element);
                        }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static By setAndroidMobileFindByToElement(AndroidFindBy annotation) {
        By by = null;
        if (null != annotation) {
            String id = annotation.id();
            String xpath = annotation.xpath();
            String accessibility = annotation.accessibility();
            String uiAutomator = annotation.uiAutomator();
            String tagName = annotation.tagName();
            if (!id.isEmpty()) {
                by = AppiumBy.id(id);
            } else if (!xpath.isEmpty()) {
                by = AppiumBy.xpath(xpath);
            }
            else if (!accessibility.isEmpty()) {
                by = AppiumBy.accessibilityId(accessibility);
            }
            else if (!uiAutomator.isEmpty()) {
                by = AppiumBy.androidUIAutomator(uiAutomator);
            }
            else if (!tagName.isEmpty()) {
                by = AppiumBy.androidViewTag(tagName);
            }

        }
        return by;
    }

    private static By setFindByToElement(FindBy annotation) {
        By by = null;
        if (null != annotation) {
            String id = annotation.id();
            String xpath = annotation.xpath();
            String buttonContainsText = annotation.buttonContainsText();
            String buttonText = annotation.buttonText();
            String linkContainsText = annotation.linkContainsText();
            String linkText = annotation.linkText();
            String className = annotation.className();
            String name = annotation.name();
            String type = annotation.type();

            if (!id.isEmpty()) {
                by = By.id(id);
            } else if (!xpath.isEmpty()) {
                by = By.xpath(xpath);
            }
            else if (!buttonContainsText.isEmpty()) {
                by = By.xpath(String.format("//button[contains(.,'%s')]", buttonContainsText));
            }
            else if (!buttonText.isEmpty()) {
                by = By.xpath(String.format("//button[text()='%s']", buttonText));
            }
            else if (!linkContainsText.isEmpty()) {
                by = By.xpath(String.format("//a[contains(.,'%s')]", linkContainsText));
            }
            else if (!linkText.isEmpty()) {
                by = By.xpath(String.format("//a[text()='%s']", linkText));
            }
            else if (!className.isEmpty()) {
                by = (By.className(className));
            }
            else if (!name.isEmpty()) {
                by = (By.name(name));
            }
            else if (!type.isEmpty()) {
                by = (By.xpath(String.format("//*[@type='%s']", type)));
            }
        }
        return by;
    }

        private static Object createList(GlobalDriver driver, ClassLoader loader, By by, Class<Element> clazz){
            InvocationHandler handler = new CustomInvocationHandler(driver, by, clazz);
            List<Element> elements = (List<Element>) Proxy.newProxyInstance(loader, new Class[] {List.class}, handler);
            return elements;
        }
}


