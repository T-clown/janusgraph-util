package com.janusgraph.enums;

/**
 * schema中PropertyKey可支持的基本数据类型.
 *
 */
public enum DataType {
  /**
   *
   */
  Byte(java.lang.Byte.class),
  Short(java.lang.Short.class),
  Char(java.lang.Character.class),


  Integer(java.lang.Integer.class),
  Long(java.lang.Long.class),
  Float(java.lang.Float.class),

  Double(java.lang.Double.class),
  Boolean(java.lang.Boolean.class),
  String(java.lang.String.class),

  Enum(java.lang.Enum.class),
  Class(java.lang.Class.class), NULL(null);

  private java.lang.Class<?> clazz;

  private DataType(java.lang.Class<?> clazz) {
    this.clazz = clazz;
  }

  public java.lang.Class<?> getClazz() {
    return clazz;
  }
};