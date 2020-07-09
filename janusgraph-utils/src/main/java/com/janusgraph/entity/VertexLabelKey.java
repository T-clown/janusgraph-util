package com.janusgraph.entity;

import java.io.Serializable;

/**
 * schema中VertexLabelKey实体信息.
 *
 */
public class VertexLabelKey implements Serializable {

  private static final long serialVersionUID = 3755222377192664944L;

  private String name;

  private String description;

  public VertexLabelKey() {}

  public VertexLabelKey(String name) {
    super();
    this.name = name;
  }

  public VertexLabelKey(String name, String description) {
    super();
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
