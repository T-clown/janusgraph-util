package com.janusgraph;

import com.janusgraph.entity.Schema;
import com.janusgraph.utils.GraphFactory;
import com.janusgraph.utils.SchemaBuilder;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        GraphFactory factory = new GraphFactory();
        GraphTraversalSource g = factory.getG();
        Long next = g.V().count().next();
        System.out.println(next);
        SchemaBuilder.buildSchema(new Schema());
    }

}
