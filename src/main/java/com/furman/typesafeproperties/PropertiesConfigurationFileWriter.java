package com.furman.typesafeproperties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

public class PropertiesConfigurationFileWriter {

    private static final PropertiesConfigurationFileWriter INSTANCE = new PropertiesConfigurationFileWriter();
    static final String DEFAULT_PLACEHOLDER_VALUE = "TODO";
    private final PropertiesFileNameCalculator propertiesFileNameCalculator;

    private PropertiesConfigurationFileWriter() {
        propertiesFileNameCalculator = new PropertiesFileNameCalculator();
    }

    public static PropertiesConfigurationFileWriter getInstance() {
        return INSTANCE;
    }

    public void createAndWriteConfigurationFile(Class<?> configurationInterface, OutputStream toWrite) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(toWrite));
        for (Method method : configurationInterface.getDeclaredMethods()) {
            writer.write(propertiesFileNameCalculator.getPropertyNameFromMethodName(method.getName()));
            writer.write(" = ");
            writer.write(DEFAULT_PLACEHOLDER_VALUE);
            writer.write("\n");
        }
        writer.close();
    }
}
