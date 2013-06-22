package com.furman.typesafeproperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PropertiesFileNameCalculator {

    private static final Pattern WORD_BEGINNING_WITH_CAPITAL_LETTER = Pattern.compile("[A-Z][^A-Z]*");

    public String getPropertyNameFromMethodName(String methodName) {
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
