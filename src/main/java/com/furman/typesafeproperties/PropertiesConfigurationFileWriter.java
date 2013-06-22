package com.furman.typesafeproperties;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static void main(final String[] args) throws ClassNotFoundException, IOException {
        getInstance().createAndWriteConfigurationFile(Class.forName(args[0]), new FileOutputStream(args[1]));
    }

    public void createAndWriteConfigurationFile(Class<?> configurationInterface, OutputStream toWrite) throws IOException {
        List<String> lines = createSortedListOfConfigLines(configurationInterface);
        writeConfigLinesToFile(toWrite, lines);
    }

    private void writeConfigLinesToFile(OutputStream toWrite, List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(toWrite));
        for (String line : lines) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }

    private List<String> createSortedListOfConfigLines(Class<?> configurationInterface) {
        List<String> lines = new ArrayList<>();
        for (Method method : configurationInterface.getDeclaredMethods()) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(propertiesFileNameCalculator.getPropertyNameFromMethodName(method.getName()));
            lineBuilder.append(" = ");
            lineBuilder.append(DEFAULT_PLACEHOLDER_VALUE);
            lines.add(lineBuilder.toString());
        }
        Collections.sort(lines);
        return lines;
    }
}
