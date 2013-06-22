package com.furman.typesafeproperties;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PropertiesFileConfigurationMethodMapperTest {

    private TestConfig config;

    @Before
    public void setUp() throws IOException {
        config = ConfigurationFactory.newInstance().createConfiguration(TestConfig.class, "/config/testconfig.properties");
    }

    @Test
    public void shouldRetrieveOneWordLowerCaseConfigurationElementByName() {
        assertThat(config.getElement(), equalTo("value"));
    }

    @Test
    public void shouldRetrieveMultipleWordDotSeparatedConfigurationElement() {
        assertThat(config.getSomeNestedElement(), equalTo("some value"));
    }

    @Test
    public void shouldRetrieveIntegerConfigurationElement() {
        assertThat(config.getInteger(), equalTo(42));
    }

    @Test
    public void shouldRetrieveLongConfigurationElement() {
        assertThat(config.getLong(), equalTo(1L));
    }

    @Test
    public void shouldRetrieveBooleanConfigurationElement() {
        assertTrue(config.getBoolean());
    }

}
