package com.furman.typesafeproperties;

public class TestBusinessObject {

    private final String p1;
    private final String p2;

    public TestBusinessObject(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }
}
