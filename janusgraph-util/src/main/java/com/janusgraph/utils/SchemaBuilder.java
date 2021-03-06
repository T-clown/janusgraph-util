//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
/**
 * https://github.com/FSixteen/janusgraph-utils.git
 */
package com.janusgraph.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.janusgraph.entity.EdgeLabelKey;
import com.janusgraph.entity.IndexKey;
import com.janusgraph.entity.PropertyKey;
import com.janusgraph.entity.Schema;
import com.janusgraph.entity.VertexLabelKey;
import com.janusgraph.enums.Mapping;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.schema.ConsistencyModifier;
import org.janusgraph.core.schema.EdgeLabelMaker;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaBuilder {
    private static final Logger log = LoggerFactory.getLogger(SchemaBuilder.class);

    public SchemaBuilder() {
    }

    public static void buildSchema(Schema schema) {

        try (GraphFactory factory = new GraphFactory()) {
            JanusGraphManagement mgmt = factory.getGraph().openManagement();
            makePropertyKey(mgmt, schema.getProps());
            makeVertexLabel(mgmt, schema.getVertices());
            makeEdgeLabel(mgmt, schema.getEdges());
            buildIndex(mgmt, schema.getIndexes());
            mgmt.commit();
        } catch (Exception e) {
            log.error("Schema Build Failed,e:{}", e.getMessage());
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
            while (var2.hasNext()) {
                EdgeLabelKey labelKey = (EdgeLabelKey)var2.next();
                if (!mgmt.containsEdgeLabel(labelKey.getName())) {
                    EdgeLabelMaker edgeLabelMaker = mgmt.makeEdgeLabel(labelKey.getName());
                    edgeLabelMaker.multiplicity(
                        labelKey.getMultiplicity() == null ? Multiplicity.MULTI : labelKey.getMultiplicity());
                    org.janusgraph.core.PropertyKey propertyKey = StringUtils.isBlank(labelKey.getSignature()) ? null
                        : mgmt.getPropertyKey(labelKey.getSignature());
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
            indexKeys.stream().filter(x -> x.getType() != null).forEach((i) -> {
                // 判断关系类型是否存在
                if (!mgmt.containsGraphIndex(i.getName())) {
                    JanusGraphManagement.IndexBuilder index = mgmt.buildIndex(i.getName(), i.getType().getClazz());
                    // 处理索引字段
                    for (PropertyKey p : i.getProps()) {
                        if (mgmt.containsPropertyKey(p.getName())) {
                            org.janusgraph.core.PropertyKey key = mgmt.getPropertyKey(p.getName());
                            if (p.getMapping() == null || Mapping.NULL == p.getMapping()) {
                                index.addKey(key);
                            } else {
                                index.addKey(key, p.getMapping().getMapping().asParameter());
                            }
                        }
                    }
                    // 创建唯一性索引
                    if (i.isUniqueIndex()) {
                        index.unique();
                    }
                    // 创建复合索引
                    if (i.isCompositeIndex()) {
                        mgmt.setConsistency(index.buildCompositeIndex(),
                            i.getConsistencyModifier() == null ? ConsistencyModifier.LOCK : i.getConsistencyModifier());
                    }
                    // 创建混合索引
                    if (i.isMixedIndex() && StringUtils.isNotBlank(i.getMixedIndexName())) {
                        index.buildMixedIndex(i.getMixedIndexName());
                    }
                }
            });
        }
    }

}
