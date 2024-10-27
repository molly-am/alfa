package com.alfaframe;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {

    public static String LOCAL_PROPERTIES_PATH = "src/test/resources/config.properties";
    private static Map<String, Properties> properties = new HashMap<String, Properties>();

    private static PropertyReader instance;

    private PropertyReader() {
    }

    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    public DesiredCapabilities getProperties(String fileName) {
        if (!properties.containsKey(fileName)) {
            Properties props = new Properties();
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(new File(fileName));
                props.load(fileReader);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }

            properties.put(fileName, props);
        }
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        return desiredCapabilities;
        Properties prop =  properties.get(fileName);
        for(Map.Entry pr : prop.entrySet()){
            String key = "";
            if(pr.getKey().toString().equals("udid")
                    || pr.getKey().toString().equals("deviceName")
                    || pr.getKey().toString().equals("newCommandTimeout")
                    || pr.getKey().toString().equals("waitForSelectorTimeout")
                    || pr.getKey().toString().equals("ignoreUnimportantViews")
                    || pr.getKey().toString().equals("waitForIdleTimeout")
                    || pr.getKey().toString().equals("autoGrantPermissions")
                    || pr.getKey().toString().equals("instrumentApp")
                    || pr.getKey().toString().equals("appActivity")
                    || pr.getKey().toString().equals("appPackage")
                    || pr.getKey().toString().equals("noReset")
                    || pr.getKey().toString().equals("platformVersion")
                    || pr.getKey().toString().equals("automationName")) {
                key = "appium:" + pr.getKey().toString();
            }else {
                key = pr.getKey().toString();
            }
            desiredCapabilities.setCapability(key, pr.getValue());

        }
        return desiredCapabilities;
    }
}
