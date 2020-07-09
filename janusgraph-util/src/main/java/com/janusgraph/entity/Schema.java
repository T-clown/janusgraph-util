//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.entity;

import java.util.List;

public class Schema {
    private List<PropertyKey> props;
    private List<VertexLabelKey> vertices;
    private List<EdgeLabelKey> edges;
    private List<IndexKey> indexes;

    public Schema() {
    }

    public Schema(List<PropertyKey> props, List<VertexLabelKey> vertices, List<EdgeLabelKey> edges, List<IndexKey> indexes) {
        this.props = props;
        this.vertices = vertices;
        this.edges = edges;
        this.indexes = indexes;
    }

    public List<PropertyKey> getProps() {
        return this.props;
    }

    public void setProps(List<PropertyKey> props) {
        this.props = props;
    }

    public List<VertexLabelKey> getVertices() {
        return this.vertices;
    }

    public void setVertices(List<VertexLabelKey> vertices) {
        this.vertices = vertices;
    }

    public List<EdgeLabelKey> getEdges() {
        return this.edges;
    }

    public void setEdges(List<EdgeLabelKey> edges) {
        this.edges = edges;
    }

    public List<IndexKey> getIndexes() {
        return this.indexes;
    }

    public void setIndexes(List<IndexKey> indexes) {
        this.indexes = indexes;
    }
}
