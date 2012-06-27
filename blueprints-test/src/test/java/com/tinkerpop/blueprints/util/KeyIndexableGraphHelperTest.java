package com.tinkerpop.blueprints.util;

import com.tinkerpop.blueprints.BaseTest;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class KeyIndexableGraphHelperTest extends BaseTest {

    public void testReIndexElements() {
        TinkerGraph graph = TinkerGraphFactory.createTinkerGraph();
        try {
            graph.getVertices("name", "marko");
            fail();
        } catch (IllegalStateException e) {
        }
        graph.createKeyIndex("name", Vertex.class);
        //KeyIndexableGraphHelper.reIndexElements(graph, graph.getVertices(), new HashSet<String>(Arrays.asList("name")));
        assertEquals(count(graph.getVertices("name", "marko")), 1);
        assertEquals(graph.getVertices("name", "marko").iterator().next(), graph.getVertex(1));
    }

}
