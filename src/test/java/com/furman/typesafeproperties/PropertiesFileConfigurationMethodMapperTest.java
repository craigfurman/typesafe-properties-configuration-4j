package com.furman.typesafeproperties;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PropertiesFileConfigurationMethodMapperTest {

    @Test
    public void shouldRetrieveOneWordLowerCaseConfigurationElementByName() throws IOException {
        TestConfig config = ConfigurationFactory.newInstance().createConfiguration(TestConfig.class, "/config/testconfig.properties");
        assertThat(config.getElement(), equalTo("value"));
    }

}
