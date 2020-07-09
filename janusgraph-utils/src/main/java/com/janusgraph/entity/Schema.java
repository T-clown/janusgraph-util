package com.janusgraph.entity;

import java.util.List;

/**
 * schema实体信息.
 *
 */
public class Schema {
  /**
   * 所有的属性信息.
   */
  private List<PropertyKey> props;
  /**
   * 所有顶点类型.
   */
  private List<VertexLabelKey> vertices;
  /**
   * 所有关系类型.
   */
  private List<EdgeLabelKey> edges;
  /**
   * 所有索引信息.
   */
  private List<IndexKey> indexes;

  public Schema() {}

  public Schema(List<PropertyKey> props, List<VertexLabelKey> vertices, List<EdgeLabelKey> edges,
                List<IndexKey> indexes) {
    this.props = props;
    this.vertices = vertices;
    this.edges = edges;
    this.indexes = indexes;
  }

  public List<PropertyKey> getProps() {
    return props;
  }

  public void setProps(List<PropertyKey> props) {
    this.props = props;
  }

  public List<VertexLabelKey> getVertices() {
    return vertices;
  }

  public void setVertices(List<VertexLabelKey> vertices) {
    this.vertices = vertices;
  }

  public List<EdgeLabelKey> getEdges() {
    return edges;
  }

  public void setEdges(List<EdgeLabelKey> edges) {
    this.edges = edges;
  }

  public List<IndexKey> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<IndexKey> indexes) {
    this.indexes = indexes;
  }

}
