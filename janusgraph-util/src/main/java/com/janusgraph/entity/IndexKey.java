//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.entity;

import com.janusgraph.enums.IndexType;
import java.io.Serializable;
import java.util.List;
import org.janusgraph.core.schema.ConsistencyModifier;

public class IndexKey implements Serializable {
  private static final long serialVersionUID = -6611572892836792876L;
  private String name;
  private IndexType type;
  private ConsistencyModifier consistencyModifier;
  private List<IndexPropertyKey> props;
  private Boolean uniqueIndex;
  private Boolean compositeIndex;
  private Boolean mixedIndex;
  private String mixedIndexName;

  public IndexKey() {
    this.consistencyModifier = ConsistencyModifier.DEFAULT;
    this.uniqueIndex = false;
    this.compositeIndex = false;
    this.mixedIndex = false;
  }

  public IndexKey(String name, IndexType type, ConsistencyModifier consistencyModifier, List<IndexPropertyKey> props, Boolean uniqueIndex, Boolean compositeIndex, Boolean mixedIndex, String mixedIndexName) {
    this.consistencyModifier = ConsistencyModifier.DEFAULT;
    this.uniqueIndex = false;
    this.compositeIndex = false;
    this.mixedIndex = false;
    this.name = name;
    this.type = type;
    this.consistencyModifier = consistencyModifier;
    this.props = props;
    this.uniqueIndex = uniqueIndex;
    this.compositeIndex = compositeIndex;
    this.mixedIndex = mixedIndex;
    this.mixedIndexName = mixedIndexName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IndexType getType() {
    return this.type;
  }

  public void setType(IndexType type) {
    this.type = type;
  }

  public ConsistencyModifier getConsistencyModifier() {
    return this.consistencyModifier;
  }

  public void setConsistencyModifier(ConsistencyModifier consistencyModifier) {
    this.consistencyModifier = consistencyModifier;
  }

  public List<IndexPropertyKey> getProps() {
    return this.props;
  }

  public void setProps(List<IndexPropertyKey> props) {
    this.props = props;
  }

  public Boolean isUniqueIndex() {
    return this.uniqueIndex;
  }

  public void setUniqueIndex(Boolean uniqueIndex) {
    this.uniqueIndex = uniqueIndex;
  }

  public Boolean isCompositeIndex() {
    return this.compositeIndex;
  }

  public void setCompositeIndex(Boolean compositeIndex) {
    this.compositeIndex = compositeIndex;
  }

  public Boolean isMixedIndex() {
    return this.mixedIndex;
  }

  public void setMixedIndex(Boolean mixedIndex) {
    this.mixedIndex = mixedIndex;
  }

  public String getMixedIndexName() {
    return this.mixedIndexName;
  }

  public void setMixedIndexName(String mixedIndexName) {
    this.mixedIndexName = mixedIndexName;
  }
}
