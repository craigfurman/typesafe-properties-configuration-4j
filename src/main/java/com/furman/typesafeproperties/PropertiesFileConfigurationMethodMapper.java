package com.furman.typesafeproperties;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

class PropertiesFileConfigurationMethodMapper implements InvocationHandler {

    private Properties configMap;

    public PropertiesFileConfigurationMethodMapper(String propertiesFileClasspathAddress) throws IOException {
        configMap = new Properties();
        configMap.load(getClass().getResourceAsStream(propertiesFileClasspathAddress));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return configMap.getProperty(method.getName().substring(3).toLowerCase());
    }

}