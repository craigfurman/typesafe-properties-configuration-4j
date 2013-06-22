package com.furman.typesafeproperties;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class ConfigurationFactory {

    private static final ConfigurationFactory INSTANCE = new ConfigurationFactory();

    private ConfigurationFactory() {
    }

    public static ConfigurationFactory getInstance() {
        return INSTANCE;
    }

    public <T> T createConfiguration(Class<T> configurationInterface, String propertiesFileClasspathAddress, boolean throwExceptionWhenConfigurationElementNotFound) throws IOException {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{configurationInterface},
                new PropertiesFileConfigurationMethodMapper(propertiesFileClasspathAddress, throwExceptionWhenConfigurationElementNotFound));
    }

    public <T> T createConfiguration(Class<T> configurationInterface, String propertiesFileClasspathAddress) throws IOException {
        return createConfiguration(configurationInterface, propertiesFileClasspathAddress, false);
    }

}
