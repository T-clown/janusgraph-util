//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphFactory.Builder;
import org.janusgraph.core.JanusGraphTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GraphFactory implements Closeable, Serializable {
    private static final Logger log = LoggerFactory.getLogger(GraphFactory.class);
    private static final long serialVersionUID = 1L;
    private Builder builder = null;
    private JanusGraph graph = null;

    public GraphFactory() throws IOException {
        this.init(null);
    }

    public GraphFactory(String path) throws IOException {
        this.init(path);
    }

    public Builder getBuilder() {
        return this.builder;
    }

    public JanusGraph getGraph() {
        return this.graph;
    }

    public GraphTraversalSource getG() {
        return this.graph.traversal();
    }

    public JanusGraphTransaction getTx() {
        return this.graph.newTransaction();
    }

    @Override
    public void close() {
        if (null != this.graph) {
            this.graph.close();
            log.info("......JanusGraph Closed......");
        }
    }

    private void init(String filePath) throws IOException {
        this.builder = JanusGraphFactory.build();
        Map<String, Object> properties = this.getProperties(filePath);
        if (MapUtils.isNotEmpty(properties)) {
            properties.forEach((key, value) -> this.builder.set(key, value));
        }

        this.graph = this.builder.open();
        log.info("......Init JanusGraph Success......");
    }

    private Map<String, Object> getProperties(String filePath) throws IOException {
        InputStream is = null;
        Map<String, Object> properties = new HashMap();
        Properties p = new Properties();
        try {
            is = StringUtils.isBlank(filePath) ? this.getClass().getResourceAsStream("/janusgarph.properties")
                : new FileInputStream(filePath);
            p.load(is);
            p.forEach((key, value) -> properties.put((String)key, value));
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return properties;
    }
}
