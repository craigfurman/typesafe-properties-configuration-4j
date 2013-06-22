package com.furman.typesafeproperties;

public interface TestConfig {
    String getElement();

    String getSomeNestedElement();

    int getInteger();

    long getLong();

    boolean getBoolean();

    String getNonExistentString();

    int getNonExistentInt();

    boolean getNonExistentBoolean();

    long getNonExistentLong();
}
