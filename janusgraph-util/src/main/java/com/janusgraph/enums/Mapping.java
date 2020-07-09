//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.enums;

public enum Mapping {
  DEFAULT(org.janusgraph.core.schema.Mapping.DEFAULT),
  TEXT(org.janusgraph.core.schema.Mapping.TEXT),
  STRING(org.janusgraph.core.schema.Mapping.STRING),
  TEXTSTRING(org.janusgraph.core.schema.Mapping.TEXTSTRING),
  PREFIX_TREE(org.janusgraph.core.schema.Mapping.PREFIX_TREE),
  NULL((org.janusgraph.core.schema.Mapping)null);

  private org.janusgraph.core.schema.Mapping mapping;

  private Mapping(org.janusgraph.core.schema.Mapping mapping) {
    this.mapping = mapping;
  }

  public org.janusgraph.core.schema.Mapping getMapping() {
    return this.mapping;
  }
}
