package com.tinkerpop.blueprints.util.io.graphson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

public class GraphSONWriterTest {

    @Test
    public void outputGraphNoEmbeddedTypes() throws JSONException, IOException {
        Graph g = TinkerGraphFactory.createTinkerGraph();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        GraphSONWriter writer = new GraphSONWriter(g);
        writer.outputGraph(stream, null, null, false);

        stream.flush();
        stream.close();

        String jsonString = new String(stream.toByteArray());

        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readValue(jsonString, JsonNode.class);

        // ensure that the JSON conforms to basic structure and that the right
        // number of graph elements are present.  other tests already cover element formatting
        Assert.assertNotNull(rootNode);
        Assert.assertTrue(rootNode.has(GraphSONTokens.VERTICES));

        ArrayNode vertices = (ArrayNode) rootNode.get(GraphSONTokens.VERTICES);
        Assert.assertEquals(6, vertices.size());

        Assert.assertTrue(rootNode.has(GraphSONTokens.EDGES));

        ArrayNode edges = (ArrayNode) rootNode.get(GraphSONTokens.EDGES);
        Assert.assertEquals(6, edges.size());
    }

    @Test
    public void outputGraphWithEmbeddedTypes() throws JSONException, IOException {
        Graph g = TinkerGraphFactory.createTinkerGraph();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        GraphSONWriter writer = new GraphSONWriter(g);
        writer.outputGraph(stream, null, null, true);

        stream.flush();
        stream.close();

        String jsonString = new String(stream.toByteArray());

        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readValue(jsonString, JsonNode.class);

        // ensure that the JSON conforms to basic structure and that the right
        // number of graph elements are present.  other tests already cover element formatting
        Assert.assertNotNull(rootNode);
        Assert.assertTrue(rootNode.has(GraphSONTokens.EMBEDDED_TYPES));
        Assert.assertTrue(rootNode.get(GraphSONTokens.EMBEDDED_TYPES).getBooleanValue());

        Assert.assertTrue(rootNode.has(GraphSONTokens.VERTICES));

        ArrayNode vertices = (ArrayNode) rootNode.get(GraphSONTokens.VERTICES);
        Assert.assertEquals(6, vertices.size());

        Assert.assertTrue(rootNode.has(GraphSONTokens.EDGES));

        ArrayNode edges = (ArrayNode) rootNode.get(GraphSONTokens.EDGES);
        Assert.assertEquals(6, edges.size());
    }
}
