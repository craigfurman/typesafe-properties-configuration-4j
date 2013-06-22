package com.furman.typesafeproperties;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class PropertiesConfigurationFileWriterTest {

    @Test
    public void shouldGenerateConfigurationPropertiesFileInAlphabeticalOrderFromInterface() throws IOException {
        PipedInputStream pipe = new PipedInputStream();
        PropertiesConfigurationFileWriter.getInstance().createAndWriteConfigurationFile(TestConfigToWrite.class, new PipedOutputStream(pipe));
        BufferedReader reader = new BufferedReader(new InputStreamReader(pipe));
        assertThat(reader.readLine(), equalTo(String.format("integer = %s", PropertiesConfigurationFileWriter.DEFAULT_PLACEHOLDER_VALUE)));
        assertThat(reader.readLine(), equalTo(String.format("string.element = %s", PropertiesConfigurationFileWriter.DEFAULT_PLACEHOLDER_VALUE)));
        assertNull(reader.readLine());
        reader.close();
    }

}
