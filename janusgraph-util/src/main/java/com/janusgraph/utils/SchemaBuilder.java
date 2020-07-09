//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.janusgraph.utils;

import com.janusgraph.entity.EdgeLabelKey;
import com.janusgraph.entity.IndexKey;
import com.janusgraph.entity.IndexPropertyKey;
import com.janusgraph.entity.PropertyKey;
import com.janusgraph.entity.Schema;
import com.janusgraph.entity.VertexLabelKey;
import com.janusgraph.enums.Mapping;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.schema.ConsistencyModifier;
import org.janusgraph.core.schema.EdgeLabelMaker;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.Parameter;
import org.janusgraph.core.schema.JanusGraphManagement.IndexBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaBuilder {
    private static final Logger log = LoggerFactory.getLogger(SchemaBuilder.class);

    public SchemaBuilder() {
    }

    public static void buildSchema(Schema schema) {
        GraphFactory graphFactory = new GraphFactory();
        JanusGraphManagement mgmt = graphFactory.getGraph().openManagement();

        try {
            makePropertyKey(mgmt, schema.getProps());
            makeVertexLabel(mgmt, schema.getVertices());
            makeEdgeLabel(mgmt, schema.getEdges());
            buildIndex(mgmt, schema.getIndexes());
            mgmt.commit();
        } catch (Exception var7) {
            log.error("e:{}", var7);
        } finally {
            graphFactory.close();
        }

    }

    private static void makePropertyKey(JanusGraphManagement mgmt, List<PropertyKey> props) {
        if (CollectionUtils.isNotEmpty(props)) {
            props.forEach((p) -> {
                if (!mgmt.containsPropertyKey(p.getName())) {
                    mgmt.makePropertyKey(p.getName()).dataType(p.getType().getClazz()).make();
                }

            });
        }

    }

    private static void makeVertexLabel(JanusGraphManagement mgmt, List<VertexLabelKey> labelKeys) {
        if (CollectionUtils.isNotEmpty(labelKeys)) {
            labelKeys.forEach((v) -> {
                if (!mgmt.containsVertexLabel(v.getName())) {
                    mgmt.makeVertexLabel(v.getName()).make();
                }

            });
        }

    }

    private static void makeEdgeLabel(JanusGraphManagement mgmt, List<EdgeLabelKey> labelKeys) {
        if (CollectionUtils.isNotEmpty(labelKeys)) {
            Iterator var2 = labelKeys.iterator();

            while(var2.hasNext()) {
                EdgeLabelKey labelKey = (EdgeLabelKey)var2.next();
                if (!mgmt.containsEdgeLabel(labelKey.getName())) {
                    EdgeLabelMaker edgeLabelMaker = mgmt.makeEdgeLabel(labelKey.getName());
                    edgeLabelMaker.multiplicity(labelKey.getMultiplicity() == null ? Multiplicity.MULTI : labelKey.getMultiplicity());
                    org.janusgraph.core.PropertyKey propertyKey = StringUtils.isBlank(labelKey.getSignature()) ? null : mgmt.getPropertyKey(labelKey.getSignature());
                    if (propertyKey != null) {
                        edgeLabelMaker.signature(propertyKey);
                    }

                    edgeLabelMaker.make();
                }
            }
        }

    }

    private static void buildIndex(JanusGraphManagement mgmt, List<IndexKey> indexKeys) {
        if (CollectionUtils.isNotEmpty(indexKeys)) {
            indexKeys.stream().filter((x) -> x.getType() != null).forEach((i) -> {
                if (!mgmt.containsGraphIndex(i.getName())) {
                    IndexBuilder index = mgmt.buildIndex(i.getName(), i.getType().getClazz());
                    Iterator var3 = i.getProps().iterator();

                    while(true) {
                        while(true) {
                            IndexPropertyKey p;
                            do {
                                if (!var3.hasNext()) {
                                    if (i.isUniqueIndex()) {
                                        index.unique();
                                    }

                                    if (i.isCompositeIndex()) {
                                        mgmt.setConsistency(index.buildCompositeIndex(), i.getConsistencyModifier() == null ? ConsistencyModifier.LOCK : i.getConsistencyModifier());
                                    }

                                    if (i.isMixedIndex() && StringUtils.isNotBlank(i.getMixedIndexName())) {
                                        index.buildMixedIndex(i.getMixedIndexName());
                                    }

                                    return;
                                }

                                p = (IndexPropertyKey)var3.next();
                            } while(!mgmt.containsPropertyKey(p.getName()));

                            org.janusgraph.core.PropertyKey key = mgmt.getPropertyKey(p.getName());
                            if (null != p.getMapping() && Mapping.NULL != p.getMapping()) {
                                index.addKey(key, p.getMapping().getMapping().asParameter());
                            } else {
                                index.addKey(key);
                            }
                        }
                    }
                }
            });
        }

    }
}
