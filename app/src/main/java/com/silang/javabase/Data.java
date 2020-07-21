package com.silang.javabase;

import java.io.Serializable;

public class Data implements Serializable {

    String type;

    public Data(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type='" + type + '\'' +
                '}';
    }
}
