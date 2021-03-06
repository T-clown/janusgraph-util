package com.janusgraph.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.SchemaAction;
import org.janusgraph.graphdb.database.management.GraphIndexStatusReport;
import org.janusgraph.graphdb.database.management.ManagementSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.addV;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.unfold;

@Slf4j
@Component
public class GremlinUtils {

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    public static void addVertices(GraphTraversalSource g, String label, List<Map<String, Object>> vertices) {
        if (CollectionUtils.isEmpty(vertices)) {
            return;
        }
        GraphTraversal<Vertex, Vertex> traversal = g.addV(label);
        vertices.get(0).forEach((k, v) -> traversal.property(k, v));
        if (vertices.size() > 1) {
            for (int i = 1; i < vertices.size(); i++) {
                traversal.addV(label);
                vertices.get(i).forEach((k, v) -> traversal.property(k, v));
            }
        }
        traversal.next();
    }

    /**
     * g.V().hasLabel("label").has("name", givenName).fold().coalesce(unfold(),addV("label").property("name", givenName))
     * @param g
     * @param label
     * @param uniqueProps
     * @param props
     */
    public static void addVDistinct(GraphTraversalSource g, String label, Map<String, Object> uniqueProps,
                                    Map<String, Object> props) {
        GraphTraversal<Vertex, Vertex> traversal = g.V().hasLabel(label);
        uniqueProps.forEach(traversal::has);
        GraphTraversal<Object, Vertex> addV = addV(label);
        props.forEach((k, v) -> addV.property(k, v));
        traversal.fold().coalesce(unfold(),addV);
        traversal.next();
    }

    public static boolean updateIndexStatus(String index, SchemaAction action) {
        try (GraphFactory factory = new GraphFactory()) {
            JanusGraphManagement mgmt = factory.getGraph().openManagement();
            mgmt.updateIndex(mgmt.getGraphIndex(index), action).get();
            mgmt.commit();
            GraphIndexStatusReport call = ManagementSystem.awaitGraphIndexStatus(factory.getGraph(), index).call();
            //call.getConvergedKeys();
            return call.getSucceeded();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
