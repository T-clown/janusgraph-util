package com.janusgraph.test;

import java.io.IOException;

import com.janusgraph.utils.GraphFactory;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.junit.Test;

public class JanusgraphTest {
    @Test
    public void demo() throws IOException {
        GraphFactory factory = new GraphFactory();
        JanusGraph graph = factory.getGraph();
        JanusGraphManagement mgmt = graph.openManagement();
        System.out.println(mgmt.printSchema());
    }
}
