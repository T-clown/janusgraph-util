package com.janusgraph.utils;

import java.util.List;

import com.janusgraph.entity.EdgeLabelKey;
import com.janusgraph.entity.IndexKey;
import com.janusgraph.entity.IndexPropertyKey;
import com.janusgraph.entity.Schema;
import com.janusgraph.enums.Mapping;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.ConsistencyModifier;
import org.janusgraph.core.schema.EdgeLabelMaker;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建Schema
 */
public class SchemaBuilder {
    private static final Logger log = LoggerFactory.getLogger(SchemaBuilder.class);

    public static void buildSchema(Schema schema) {
        GraphFactory graphFactory = new GraphFactory();
        JanusGraphManagement mgmt = graphFactory.getGraph().openManagement();
        try {
            makePropertyKey(mgmt, schema.getProps());

            makeVertexLabel(mgmt, schema.getVertices());

            makeEdgeLabel(mgmt, schema.getEdges());

            buildIndex(mgmt, schema.getIndexes());
            //提交
            mgmt.commit();
        } catch (Exception e) {
            log.error("e:{}", e);
        } finally {
            graphFactory.close();
        }

    }

    /**
     * 初始化属性
     *
     * @param mgmt
     * @param props
     */
    private static void makePropertyKey(JanusGraphManagement mgmt, List<com.janusgraph.entity.PropertyKey> props) {
        if (CollectionUtils.isNotEmpty(props)) {
            props.forEach((p) -> {
                // 判断属性是否存在
                if (!mgmt.containsPropertyKey(p.getName())) {
                    mgmt.makePropertyKey(p.getName()).dataType(p.getType().getClazz()).make();
                }
            });
        }
    }

    /**
     * 初始化顶点类型
     *
     * @param mgmt
     * @param labelKeys
     */
    private static void makeVertexLabel(JanusGraphManagement mgmt, List<com.janusgraph.entity.VertexLabelKey> labelKeys) {
        if (CollectionUtils.isNotEmpty(labelKeys)) {
            labelKeys.forEach((v) -> {
                // 判断顶点类型是否存在
                if (!mgmt.containsVertexLabel(v.getName())) {
                    mgmt.makeVertexLabel(v.getName()).make();
                }
            });
        }
    }

    /**
     * 初始化关系类型
     *
     * @param mgmt
     * @param labelKeys
     */
    private static void makeEdgeLabel(JanusGraphManagement mgmt, List<EdgeLabelKey> labelKeys) {
        if (CollectionUtils.isNotEmpty(labelKeys)) {
            for (EdgeLabelKey labelKey : labelKeys) {
                // 判断关系类型是否存在
                if (!mgmt.containsEdgeLabel(labelKey.getName())) {
                    EdgeLabelMaker edgeLabelMaker = mgmt.makeEdgeLabel(labelKey.getName());
                    edgeLabelMaker.multiplicity(
                        (labelKey.getMultiplicity() == null) ? Multiplicity.MULTI : labelKey.getMultiplicity());
                    PropertyKey propertyKey = StringUtils.isBlank(labelKey.getSignature()) ? null
                        : mgmt.getPropertyKey(labelKey.getSignature());
                    if (propertyKey != null) { edgeLabelMaker.signature(propertyKey); }
                    edgeLabelMaker.make();
                }
            }
        }
    }

    /**
     * 初始化索引类型
     *
     * @param mgmt
     * @param indexKeys
     */
    private static void buildIndex(JanusGraphManagement mgmt, List<IndexKey> indexKeys) {
        if (CollectionUtils.isNotEmpty(indexKeys)) {
            indexKeys.stream().filter(x -> x.getType() != null).forEach((i) -> {
                // 判断关系类型是否存在
                if (!mgmt.containsGraphIndex(i.getName())) {
                    JanusGraphManagement.IndexBuilder index = mgmt.buildIndex(i.getName(), i.getType().getClazz());
                    // 处理索引字段
                    for (IndexPropertyKey p : i.getProps()) {
                        if (mgmt.containsPropertyKey(p.getName())) {
                            PropertyKey key = mgmt.getPropertyKey(p.getName());
                            if (null == p.getMapping() || Mapping.NULL == p.getMapping()) {
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
