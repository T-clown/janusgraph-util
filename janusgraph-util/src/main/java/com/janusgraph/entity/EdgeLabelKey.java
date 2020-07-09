//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.entity;

import java.io.Serializable;
import org.janusgraph.core.Multiplicity;

public class EdgeLabelKey implements Serializable {
  private static final long serialVersionUID = -4145200612421215610L;
  private String name;
  private Multiplicity multiplicity;
  private String signature;
  private String description;

  public EdgeLabelKey() {
  }

  public EdgeLabelKey(String name, Multiplicity multiplicity, String signature) {
    this.name = name;
    this.multiplicity = multiplicity;
    this.signature = signature;
  }

  public EdgeLabelKey(String name, Multiplicity multiplicity, String signature, String description) {
    this.name = name;
    this.multiplicity = multiplicity;
    this.signature = signature;
    this.description = description;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Multiplicity getMultiplicity() {
    return this.multiplicity;
  }

  public void setMultiplicity(Multiplicity multiplicity) {
    this.multiplicity = multiplicity;
  }

  public String getSignature() {
    return this.signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
