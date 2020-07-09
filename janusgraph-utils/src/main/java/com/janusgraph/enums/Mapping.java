package com.janusgraph.enums;

/**
 * schema中Index中字段可支持的映射类型.
 */
public enum Mapping {
  /**
   *
   */
  DEFAULT(org.janusgraph.core.schema.Mapping.DEFAULT),
  TEXT(org.janusgraph.core.schema.Mapping.TEXT),

  STRING(org.janusgraph.core.schema.Mapping.STRING),
  TEXTSTRING(org.janusgraph.core.schema.Mapping.TEXTSTRING),

  PREFIX_TREE(org.janusgraph.core.schema.Mapping.PREFIX_TREE),
  NULL(null);

  private org.janusgraph.core.schema.Mapping mapping;

  private Mapping(org.janusgraph.core.schema.Mapping mapping) {
    this.mapping = mapping;
  }

  public org.janusgraph.core.schema.Mapping getMapping() {
    return mapping;
  }
}