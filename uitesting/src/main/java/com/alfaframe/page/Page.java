package com.alfaframe.page;

import com.alfaframe.DriverManager;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public class Page {

    protected<T extends Page>T initPage(Class<T> clazz){
        T page = PageFactory.initElements( DriverManager.getMobileDriver(), clazz);
        return page;
    }

    private static String pathScreenshots = "./build/classes/screenshots/";

    private static File makeScreenshot(){
        Screenshot screenshot = new AShot().takeScreenshot(DriverManager.getMobileDriver().getWebDriver());
        if( ! java.nio.file.Files.exists(Paths.get(pathScreenshots))) {
            try {
                java.nio.file.Files.createDirectories(Paths.get(pathScreenshots));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File currentScreenshot = new File(pathScreenshots + timestamp.getTime() + ".png");
        try {
            ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentScreenshot;
    }

    @Attachment(type = "image/png")
    public static byte[] attachAllureScreenshot() {
        File screenshot = makeScreenshot();
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
