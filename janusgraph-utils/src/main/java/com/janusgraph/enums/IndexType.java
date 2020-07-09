package com.janusgraph.enums;

import org.apache.tinkerpop.gremlin.structure.Element;

/**
 * schema中Index可支持的枚举类型.
 */
public enum IndexType {
    /**
     *
     */
    Vertex(org.apache.tinkerpop.gremlin.structure.Vertex.class),

    Edge(org.apache.tinkerpop.gremlin.structure.Edge.class);

    private Class<? extends Element> clazz;

    private IndexType(Class<? extends Element> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Element> getClazz() {
        return clazz;
    }
}