package com.paramInfo.popcorn.utilities;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropReader {

    public PropReader() {
        PropertyConfigurator.configure(FileHandler.getResourcesFilePath(Constants.logProperties));
    }

    public static String getProperty(String propertiesFileName, String propertyKey) {
        Properties properties=new Properties();
        File file = new File(propertiesFileName);
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String keyValue = properties.get(propertyKey).toString();
        validateAndGetProperty(propertyKey, keyValue);
        return keyValue;
    }

    private static void validateAndGetProperty(String propertyKey, String keyValue) {
        if(keyValue.isEmpty()|| keyValue.length()==0 || keyValue==null){
            System.out.println(propertyKey+ " is empty");
        }else{
            System.out.println(propertyKey+ " has value :"+keyValue);
        }
    }
}
