package com.paramInfo.popcorn.utilities;

import org.junit.Assert;
import java.io.File;

public class FileHandler {

    public static String getResourcesFilePath(String fileName){
        File file = new File(FileHandler.class.getClassLoader().getResource("properties/"+fileName).getFile());
        String fileAbsolutePath = file.getAbsolutePath();
        if(fileAbsolutePath.isEmpty())
            Assert.fail("File with name "+fileName+"doesn't exists");

            return fileAbsolutePath;
    }
}
