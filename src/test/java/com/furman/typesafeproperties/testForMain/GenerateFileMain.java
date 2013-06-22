package com.furman.typesafeproperties.testForMain;

import com.furman.typesafeproperties.PropertiesConfigurationFileWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateFileMain {
    public static void main(final String[] args) throws ClassNotFoundException, IOException {
        PropertiesConfigurationFileWriter.getInstance().createAndWriteConfigurationFile(Class.forName(args[0]), new FileOutputStream(args[1]));
    }
}
