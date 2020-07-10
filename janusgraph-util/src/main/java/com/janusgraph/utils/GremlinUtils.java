package com.janusgraph.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GremlinUtils {

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    public static void addVertices(GraphTraversalSource g, String label, List<Map<String, Object>> vertices) {
        if (CollectionUtils.isNotEmpty(vertices)) {
            GraphTraversal<Vertex, Vertex> traversal = null;
            for (Map<String, Object> vertex : vertices) {
                if (traversal == null) {
                    traversal = g.addV(label);
                } else {
                    traversal.addV(label);
                }
                Set<Entry<String, Object>> entries = vertex.entrySet();
                for (Entry<String, Object> entry : entries) {
                    traversal.property(entry.getKey(), entry.getValue());
                }
            }
            if (traversal != null) {
                traversal.next();
            }
        }
    }
}
