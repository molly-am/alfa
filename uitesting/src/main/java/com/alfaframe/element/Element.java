package com.alfaframe.element;

import com.alfaframe.DriverManager;
import com.alfaframe.GlobalDriver;
import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Arrays;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Element {

    private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(Element.class));

    private By notFormatedBy;
    protected GlobalDriver driver;
    protected By foundBy;
    private String nameForLogger = "";
    protected String name = "";
    private String formatLocator;

    public Element(GlobalDriver driver, By foundBy) {
        this.foundBy = foundBy;
        this.driver = driver;
    }

    public Element (GlobalDriver driver){
        this.driver = driver;
    }


    public Element format(Object... args) {
        nameForLogger = new StringBuilder(name).append(": ").append(Arrays.toString(args)).append("").toString();
        Class<?> byClass = notFormatedBy.getClass();
        try {
            String locatorString = FindByHelper.getStringLocator(notFormatedBy);
            foundBy = (By) byClass.getConstructor(String.class).newInstance(String.format(locatorString, args));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Failed to format locator", e);
        }
        return this;
    }

    public void click() {
        LOGGER.info("Click element : " + foundBy);
        driver.findElement(foundBy).click();
    }

    public String getText(){
        LOGGER.info("Take text value from : " + foundBy);
        waitForElementPresence();
        return driver.findElement(foundBy).getText();
    }

    public void type(String text){
        LOGGER.info("Type text : " + text + " for locator " + foundBy);
        driver.findElement(foundBy).clear();
        driver.findElement(foundBy).sendKeys(text);
    }

    public String getAttribute(String attr){
        LOGGER.info("Get attribute value with key : [" + attr + "] for locator " + foundBy);
        return driver.findElement(foundBy).getAttribute(attr);
    }

    public boolean isElementPresent(long timeoutSeconds) {
        LOGGER.info("Waiting for element '" + foundBy + "' exists during " + timeoutSeconds + "sec timeout ...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(foundBy));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForPageLoad(long timoutSec){
        LOGGER.info("Wait for element disappearance");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timoutSec));
        try {
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(foundBy)));
        } catch (NoSuchElementException exception){
            LOGGER.info("Element is absent " + foundBy);
        }
    }

    public void typeViaKeyboard(String value){
        driver.findElement(foundBy).clear();
        Arrays.stream(value.chars().toArray()).mapToObj(it -> (char) it).forEach(this::typeViaKeycode);
    }

    public boolean waitForElementPresence(){
        return isElementPresent(30);
    }

    private void typeViaKeycode(char character) {
        GlobalDriver helper = DriverManager.getMobileDriver();
        AndroidDriver driver = ((AndroidDriver) helper.getWebDriver());
        AndroidKey androidKey = null;
        switch (character) {
            case 'a': androidKey = AndroidKey.A; break;
            case 'b': androidKey = AndroidKey.B; break;
            case 'c': androidKey = AndroidKey.C; break;
            case 'd': androidKey = AndroidKey.D; break;
            case 'e': androidKey = AndroidKey.E; break;
            case 'f': androidKey = AndroidKey.F; break;
            case 'g': androidKey = AndroidKey.G; break;
            case 'h': androidKey = AndroidKey.H; break;
            case 'i': androidKey = AndroidKey.I; break;
            case 'j': androidKey = AndroidKey.J; break;
            case 'k': androidKey = AndroidKey.K; break;
            case 'l': androidKey = AndroidKey.L; break;
            case 'm': androidKey = AndroidKey.M; break;
            case 'n': androidKey = AndroidKey.N; break;
            case 'o': androidKey = AndroidKey.O; break;
            case 'p': androidKey = AndroidKey.P; break;
            case 'q': androidKey = AndroidKey.Q; break;
            case 'r': androidKey = AndroidKey.R; break;
            case 's': androidKey = AndroidKey.S; break;
            case 't': androidKey = AndroidKey.T; break;
            case 'u': androidKey = AndroidKey.U; break;
            case 'v': androidKey = AndroidKey.V; break;
            case 'w': androidKey = AndroidKey.W; break;
            case 'x': androidKey = AndroidKey.X; break;
            case 'y': androidKey = AndroidKey.Y; break;
            case 'z': androidKey = AndroidKey.Z; break;
            case '`': androidKey = AndroidKey.GRAVE; break;
            case '0': androidKey = AndroidKey.DIGIT_0; break;
            case '1': androidKey = AndroidKey.DIGIT_1; break;
            case '2': androidKey = AndroidKey.DIGIT_2; break;
            case '3': androidKey = AndroidKey.DIGIT_3; break;
            case '4': androidKey = AndroidKey.DIGIT_4; break;
            case '5': androidKey = AndroidKey.DIGIT_5; break;
            case '6': androidKey = AndroidKey.DIGIT_6; break;
            case '7': androidKey = AndroidKey.DIGIT_7; break;
            case '8': androidKey = AndroidKey.DIGIT_8; break;
            case '9': androidKey = AndroidKey.DIGIT_9; break;
            case '-': androidKey = AndroidKey.MINUS; break;
            case '=': androidKey = AndroidKey.EQUALS; break;
            case '@': androidKey = AndroidKey.AT; break;
            case '#': androidKey = AndroidKey.POUND; break;
            case '*': androidKey = AndroidKey.STAR; break;
            case ',': androidKey = AndroidKey.COMMA; break;
            case '.': androidKey = AndroidKey.PERIOD; break;
            case ' ': androidKey = AndroidKey.SPACE; break;
            case '(': androidKey = AndroidKey.LEFT_BRACKET; break;
            case ')': androidKey = AndroidKey.RIGHT_BRACKET; break;
            default:
                LOGGER.info("Keycode wasn't found, will execute script");
        }
        if (androidKey != null) {
            driver.longPressKey(new KeyEvent().withKey(androidKey));
        } else {
            driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", String.valueOf(character)));
        }
    }
}
