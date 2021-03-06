package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataFetch {


    public static String getTestData(String value){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + "//testData//TestData.Properties");
            Properties prop = new Properties();
            prop.load(fileInputStream);
            return prop.getProperty(value);
        } catch (IOException e){
            e.printStackTrace();
        }

        return  null;
    }
}
