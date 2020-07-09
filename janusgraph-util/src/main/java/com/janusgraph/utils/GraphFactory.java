//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import org.apache.commons.collections.MapUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphFactory.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GraphFactory implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(GraphFactory.class);
    private static final long serialVersionUID = 1L;
    private Builder builder = null;
    private JanusGraph graph = null;

    public GraphFactory() {
        this.init((String)null);
    }

    public GraphFactory(String path) {
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

    public void close() {
        if (null != this.graph) {
            try {
                this.graph.close();
            } catch (Exception var2) {
                log.error("e:{}", var2);
            }
        }

    }

    private void init(String filePath) {
        log.info("初始化JanusGraph..........");
        this.builder = JanusGraphFactory.build();
        Map<String, Object> properties = this.getProperties(filePath);
        if (MapUtils.isNotEmpty(properties)) {
            properties.forEach((key, value) -> {
                this.builder.set(key, value);
            });
        }

        this.graph = this.builder.open();
    }

    private Map<String, Object> getProperties(String filePath) {
        Object file = null;

        try {
            file = null == filePath ? this.getClass().getResourceAsStream("/janusgarph.properties") : new FileInputStream(filePath);
        } catch (FileNotFoundException var17) {
            log.error("e:{}", var17);
        }

        Map<String, Object> properties = new HashMap();
        Properties p = new Properties();

        try {
            p.load((InputStream)file);
            p.forEach((key, value) -> {
                properties.put((String)key, value);
            });
        } catch (Exception var15) {
            log.error("e:{}", var15);
        } finally {
            try {
                if (file != null) {
                    ((InputStream)file).close();
                }
            } catch (IOException var14) {
                log.error("e:{}", var14);
            }

        }

        return properties;
    }
}
