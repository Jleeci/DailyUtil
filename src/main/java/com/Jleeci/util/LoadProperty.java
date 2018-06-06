

package com.Jleeci.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperty {
    public static Properties pro = new Properties();
    static {

        InputStream inStream = LoadProperty.class.getClassLoader()
                .getResourceAsStream("util.properties");
        try {
            pro.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
