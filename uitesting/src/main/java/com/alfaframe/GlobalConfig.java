package com.alfaframe;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import lombok.Getter;

@Getter
public class GlobalConfig {

    private String platformName;
    private DesiredCapabilities desiredCapabilities;
    private volatile static GlobalConfig instance;

    private GlobalConfig(){
        desiredCapabilities = PropertyReader.getInstance().getProperties(PropertyReader.LOCAL_PROPERTIES_PATH);

        if(desiredCapabilities.getCapability("platformName").toString().isEmpty()){
            throw new NoSuchElementException("platformName is empty in config.properties");
        }else {
            this.platformName = desiredCapabilities.getCapability("platformName").toString();
        }
    }

    public static GlobalConfig getInstance() {
        if (instance == null) {
            synchronized (GlobalConfig.class) {
                if (instance == null) {
                    instance = new GlobalConfig();
                }
            }
        }
        return instance;
    }
}
