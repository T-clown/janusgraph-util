//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.enums;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public enum IndexType {
  /**
   *
   */
  Vertex(Vertex.class),
  Edge(Edge.class);

  private Class<? extends Element> clazz;

  IndexType(Class<? extends Element> clazz) {
    this.clazz = clazz;
  }

  public Class<? extends Element> getClazz() {
    return this.clazz;
  }
}
