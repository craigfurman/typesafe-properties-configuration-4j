package com.furman.typesafeproperties;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PropertiesFileConfigurationMethodMapperTest {

    private TestConfig config;
    private TestConfig configWithExceptionsEnabled;

    @Before
    public void setUp() throws IOException {
        config = ConfigurationFactory.getInstance().createConfiguration(TestConfig.class, "/config/testconfig.properties");
        configWithExceptionsEnabled = ConfigurationFactory.getInstance().createConfiguration(TestConfig.class, "/config/testconfig.properties", true);
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

    @Test
    public void shouldReturnDefaultValuesWhenElementDoesNotExist() {
        assertNull(config.getNonExistentString());
        assertThat(config.getNonExistentInt(), equalTo(0));
        assertThat(config.getNonExistentLong(), equalTo(0L));
        assertFalse(config.getNonExistentBoolean());
    }

    @Test(expected = ConfigurationException.class)
    public void shouldThrowExceptionWhenSpecifiedInFactoryAndConfigurationElementDoesNotExist() throws  ConfigurationException {
        configWithExceptionsEnabled.getNonExistentStringWithException();
    }

    @Test(expected = ConfigurationException.class)
    public void shouldThrowExceptionWhenSpecifiedInFactoryAndConfigurationElementDoesNotExistForIntegerReturnType() throws ConfigurationException {
        configWithExceptionsEnabled.getNonExistentIntWithException();
    }

}
