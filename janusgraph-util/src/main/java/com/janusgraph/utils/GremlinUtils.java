package com.janusgraph.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GremlinUtils {

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    public void addVertices(GraphTraversalSource g, String label, List<Map<String, Object>> vertices) {
        GraphTraversal<Vertex, Vertex> traversal = g.addV();
        for (Map<String, Object> vertex : vertices) {
            traversal.addV(label);
            vertex.forEach((k, v) -> traversal.property(k, v));
        }
        traversal.next();
    }
}
