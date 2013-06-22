package com.furman.typesafeproperties;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PropertiesFileConfigurationMethodMapper implements InvocationHandler {

    private Properties configMap;

    private static final Pattern WORD_BEGINNING_WITH_CAPITAL_LETTER = Pattern.compile("[A-Z][^A-Z]*");

    public PropertiesFileConfigurationMethodMapper(String propertiesFileClasspathAddress) throws IOException {
        configMap = new Properties();
        configMap.load(getClass().getResourceAsStream(propertiesFileClasspathAddress));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String configurationElementAsString = configMap.getProperty(getPropertyNameFromMethodName(method.getName()));
        Class<?> returnType = method.getReturnType();
        return resolveReturnTypeOfElement(configurationElementAsString, returnType);
    }

    private Object resolveReturnTypeOfElement(String configurationElementAsString, Class<?> returnType) {
        if (returnType.equals(int.class)) {
            return Integer.parseInt(configurationElementAsString);
        }
        else if (returnType.equals(long.class)) {
            return Long.parseLong(configurationElementAsString);
        }
        else if (returnType.equals(boolean.class)) {
            return Boolean.parseBoolean(configurationElementAsString);
        }
        return configurationElementAsString;
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