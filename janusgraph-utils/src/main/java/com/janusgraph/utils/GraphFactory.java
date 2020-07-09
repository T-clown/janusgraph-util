package com.janusgraph.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GraphFactory implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(GraphFactory.class);
    private static final long serialVersionUID = 1L;
    private JanusGraphFactory.Builder builder = null;

    private JanusGraph graph = null;

    public GraphFactory() {
        init(null);
    }

    public GraphFactory(String path) {
        init(path);
    }

    public JanusGraphFactory.Builder getBuilder() {
        return builder;
    }

    public JanusGraph getGraph() {
        return graph;
    }

    public GraphTraversalSource getG() {
        return this.graph.traversal();
    }

    public JanusGraphTransaction getTx() {
        return this.graph.newTransaction();
    }

    public void close() {
        if (null != graph) {
            try {
                graph.close();
            } catch (Exception e) {
                log.error("e:{}", e);
            }
        }
    }

    private void init(String filePath) {
        log.info("初始化JanusGraph..........");
        builder = JanusGraphFactory.build();
        Map<String, Object> properties = getProperties(filePath);
        if (MapUtils.isNotEmpty(properties)) {
            properties.forEach((key, value) -> builder.set(key, value));
        }
        graph = builder.open();
    }

    private Map<String, Object> getProperties(String filePath) {
        InputStream file = null;
        try {
            file = (null == filePath) ? this.getClass().getResourceAsStream(
                "/janusgarph.properties") : new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            log.error("e:{}", e);
        }
        Map<String, Object> properties = new HashMap<>();
        Properties p = new Properties();
        try {
            p.load(file);
            p.forEach((key, value) -> properties.put((String)key, value));
        } catch (Exception e) {
            log.error("e:{}", e);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                log.error("e:{}", e);
            }
        }
        return properties;
    }

}
