//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.entity;

import java.io.Serializable;

public class VertexLabelKey implements Serializable {
    private static final long serialVersionUID = 3755222377192664944L;
    private String name;
    private String description;

    public VertexLabelKey() {
    }

    public VertexLabelKey(String name) {
        this.name = name;
    }

    public VertexLabelKey(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
