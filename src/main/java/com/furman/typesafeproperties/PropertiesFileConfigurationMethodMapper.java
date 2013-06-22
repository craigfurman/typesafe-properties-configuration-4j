package com.furman.typesafeproperties;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PropertiesFileConfigurationMethodMapper implements InvocationHandler {

    private static final Pattern WORD_BEGINNING_WITH_CAPITAL_LETTER = Pattern.compile("[A-Z][^A-Z]*");

    private Properties configMap;
    private boolean throwExceptionWhenConfigurationElementNotFound;

    public PropertiesFileConfigurationMethodMapper(String propertiesFileClasspathAddress, boolean throwExceptionWhenConfigurationElementNotFound) throws IOException {
        this.throwExceptionWhenConfigurationElementNotFound = throwExceptionWhenConfigurationElementNotFound;
        configMap = new Properties();
        configMap.load(getClass().getResourceAsStream(propertiesFileClasspathAddress));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String propertyName = getPropertyNameFromMethodName(method.getName());
        String configurationElementAsString = configMap.getProperty(propertyName);
        return resolveReturnTypeOfElement(configurationElementAsString, method.getReturnType(), propertyName);
    }

    private Object resolveReturnTypeOfElement(String configurationElementAsString, Class<?> returnType, String propertyName) throws ConfigurationException {
        if (configurationElementAsString == null) {
            return handleNonExistentElement(returnType, propertyName);
        }
        else if (returnType.equals(int.class)) {
            return Integer.parseInt(configurationElementAsString);
        } else if (returnType.equals(long.class)) {
            return Long.parseLong(configurationElementAsString);
        } else if (returnType.equals(boolean.class)) {
            return Boolean.parseBoolean(configurationElementAsString);
        }
        return configurationElementAsString;
    }

    private Object handleNonExistentElement(Class<?> returnType, String propertyName) throws ConfigurationException {
        if (throwExceptionWhenConfigurationElementNotFound) {
            throw new ConfigurationException(propertyName + " not found in configuration file.");
        }
        else if (returnType.equals(int.class)) {
            return 0;
        }
        else if (returnType.equals(long.class)) {
            return 0L;
        }
        else if (returnType.equals(boolean.class)) {
            return false;
        }
        return null;
    }

    private String getPropertyNameFromMethodName(String methodName) {
        Matcher methodNameMatcher = WORD_BEGINNING_WITH_CAPITAL_LETTER.matcher(methodName);
        StringBuilder propertyNameBuilder = new StringBuilder();
        while (methodNameMatcher.find()) {
            if (propertyNameBuilder.length() > 0) {
                propertyNameBuilder.append('.');
            }
            propertyNameBuilder.append(methodNameMatcher.group().toLowerCase());
        }
        return propertyNameBuilder.toString();
    }

}