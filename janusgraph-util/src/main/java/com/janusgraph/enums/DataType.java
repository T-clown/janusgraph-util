//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.enums;

public enum DataType {
  Byte(Byte.class),
  Short(Short.class),
  Char(Character.class),
  Integer(Integer.class),
  Long(Long.class),
  Float(Float.class),
  Double(Double.class),
  Boolean(Boolean.class),
  String(String.class),
  Enum(Enum.class),
  Class(Class.class),
  NULL((Class)null);

  private Class<?> clazz;

  private DataType(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Class<?> getClazz() {
    return this.clazz;
  }
}
