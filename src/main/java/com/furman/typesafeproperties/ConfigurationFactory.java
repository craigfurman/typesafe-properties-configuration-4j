package com.furman.typesafeproperties;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URL;

public class ConfigurationFactory {

    private static final ConfigurationFactory INSTANCE = new ConfigurationFactory();

    private ConfigurationFactory() {
    }

    public static ConfigurationFactory getInstance() {
        return INSTANCE;
    }

    public <T> T createConfiguration(Class<T> configurationInterface, URL propertiesFile, boolean throwExceptionWhenConfigurationElementNotFound) throws IOException {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{configurationInterface},
                new PropertiesFileConfigurationMethodMapper(propertiesFile, throwExceptionWhenConfigurationElementNotFound));
    }

    public <T> T createConfiguration(Class<T> configurationInterface, URL propertiesFile) throws IOException {
        return createConfiguration(configurationInterface, propertiesFile, false);
    }

}
