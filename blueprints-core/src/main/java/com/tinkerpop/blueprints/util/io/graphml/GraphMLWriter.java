package com.tinkerpop.blueprints.util.io.graphml;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.io.LexicographicalElementComparator;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;

import org.xmlpull.v1.XmlSerializer;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.io.LexicographicalElementComparator;

/**
 * GraphMLWriter writes a Graph to a GraphML OutputStream.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Joshua Shinavier (http://fortytwo.net)
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class GraphMLWriter {

    private final Graph graph;
    private boolean normalize = false;
    private Map<String, String> vertexKeyTypes = null;
    private Map<String, String> edgeKeyTypes = null;

    private String xmlSchemaLocation = null;
    private String edgeLabelKey = null;

    /**
     * @param graph the Graph to pull the data from
     */
    public GraphMLWriter(final Graph graph) {
        this.graph = graph;
    }

    /**
     * @param xmlSchemaLocation the location of the GraphML XML Schema instance
     */
    public void setXmlSchemaLocation(String xmlSchemaLocation) {
        this.xmlSchemaLocation = xmlSchemaLocation;
    }

    /**
     * Set the name of the edge label in the GraphML. When this value is not set the value of the Edge.getLabel()
     * is written as a "label" attribute on the edge element.  This does not validate against the GraphML schema.
     * If this value is set then the the value of Edge.getLabel() is written as a data element on the edge and
     * the appropriate key element is added to define it in the GraphML
     *
     * @param edgeLabelKey if the label of an edge will be handled by the data property.
     */
    public void setEdgeLabelKey(String edgeLabelKey) {
        this.edgeLabelKey = edgeLabelKey;
    }

    /**
     * @param normalize whether to normalize the output. Normalized output is deterministic with respect to the order of
     *                  elements and properties in the resulting XML document, and is compatible with line diff-based tools
     *                  such as Git. Note: normalized output is memory-intensive and is not appropriate for very large graphs.
     */
    public void setNormalize(final boolean normalize) {
        this.normalize = normalize;
    }

    /**
     * @param vertexKeyTypes a Map of the data types of the vertex keys
     */
    public void setVertexKeyTypes(final Map<String, String> vertexKeyTypes) {
        this.vertexKeyTypes = vertexKeyTypes;
    }

    /**
     * @param edgeKeyTypes a Map of the data types of the edge keys
     */
    public void setEdgeKeyTypes(final Map<String, String> edgeKeyTypes) {
        this.edgeKeyTypes = edgeKeyTypes;
    }

    /**
     * Write the data in a Graph to a GraphML file.
     *
     * @param filename the name of the file write the Graph data (as GraphML) to
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public void outputGraph(final String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        outputGraph(fos);
        fos.close();
    }

    /**
     * Write the data in a Graph to a GraphML OutputStream.
     *
     * @param graphMLOutputStream the GraphML OutputStream to write the Graph data to
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public void outputGraph(final OutputStream graphMLOutputStream) throws IOException {

        if (null == vertexKeyTypes || null == edgeKeyTypes) {
            Map<String, String> vertexKeyTypes = new HashMap<String, String>();
            Map<String, String> edgeKeyTypes = new HashMap<String, String>();

            for (Vertex vertex : graph.getVertices()) {
                for (String key : vertex.getPropertyKeys()) {
                    if (!vertexKeyTypes.containsKey(key)) {
                        vertexKeyTypes.put(key, GraphMLWriter.getStringType(vertex.getProperty(key)));
                    }
                }
                for (Edge edge : vertex.getEdges(Direction.OUT)) {
                    for (String key : edge.getPropertyKeys()) {
                        if (!edgeKeyTypes.containsKey(key)) {
                            edgeKeyTypes.put(key, GraphMLWriter.getStringType(edge.getProperty(key)));
                        }
                    }
                }
            }

            if (null == this.vertexKeyTypes) {
                this.vertexKeyTypes = vertexKeyTypes;
            }

            if (null == this.edgeKeyTypes) {
                this.edgeKeyTypes = edgeKeyTypes;
            }
        }

        // adding the edge label key will push the label into the data portion of the graphml otherwise it
        // will live with the edge data itself (which won't validate against the graphml schema)
        if (null != this.edgeLabelKey && null != this.edgeKeyTypes && null == this.edgeKeyTypes.get(this.edgeLabelKey))
            this.edgeKeyTypes.put(this.edgeLabelKey, GraphMLTokens.STRING);

        XmlSerializer serializer = AndroidXmlFactory.newSerializer();
        serializer.setOutput(graphMLOutputStream, "UTF8");
        
        if ( this.normalize ) {
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        }

        serializer.startDocument("UTF-8", null);
        serializer.startTag("", GraphMLTokens.GRAPHML);
        serializer.attribute("", GraphMLTokens.XMLNS, GraphMLTokens.GRAPHML_XMLNS);

        //XML Schema instance namespace definition (xsi)
        serializer.attribute("", XMLConstants.XMLNS_ATTRIBUTE + ":" + GraphMLTokens.XML_SCHEMA_NAMESPACE_TAG,
                XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        //XML Schema location
        serializer.attribute("", GraphMLTokens.XML_SCHEMA_NAMESPACE_TAG + ":" + GraphMLTokens.XML_SCHEMA_LOCATION_ATTRIBUTE,
                GraphMLTokens.GRAPHML_XMLNS + " " + (this.xmlSchemaLocation == null ?
                        GraphMLTokens.DEFAULT_GRAPHML_SCHEMA_LOCATION : this.xmlSchemaLocation));

        // <key id="weight" for="edge" attr.name="weight" attr.type="float"/>
        Collection<String> keyset;

        if (normalize) {
            keyset = new ArrayList<String>();
            keyset.addAll(vertexKeyTypes.keySet());
            Collections.sort((List<String>) keyset);
        } else {
            keyset = vertexKeyTypes.keySet();
        }
        for (String key : keyset) {
            serializer.startTag("", GraphMLTokens.KEY);
            serializer.attribute("", GraphMLTokens.ID, key);
            serializer.attribute("", GraphMLTokens.FOR, GraphMLTokens.NODE);
            serializer.attribute("", GraphMLTokens.ATTR_NAME, key);
            serializer.attribute("", GraphMLTokens.ATTR_TYPE, vertexKeyTypes.get(key));
            serializer.text("").endTag("", GraphMLTokens.KEY);
        }

        if (normalize) {
            keyset = new ArrayList<String>();
            keyset.addAll(edgeKeyTypes.keySet());
            Collections.sort((List<String>) keyset);
        } else {
            keyset = edgeKeyTypes.keySet();
        }
        for (String key : keyset) {
            serializer.startTag("", GraphMLTokens.KEY);
            serializer.attribute("", GraphMLTokens.ID, key);
            serializer.attribute("", GraphMLTokens.FOR, GraphMLTokens.EDGE);
            serializer.attribute("", GraphMLTokens.ATTR_NAME, key);
            serializer.attribute("", GraphMLTokens.ATTR_TYPE, edgeKeyTypes.get(key));
            serializer.text("").endTag("", GraphMLTokens.KEY);
        }

        serializer.startTag("", GraphMLTokens.GRAPH);
        serializer.attribute("", GraphMLTokens.ID, GraphMLTokens.G);
        serializer.attribute("", GraphMLTokens.EDGEDEFAULT, GraphMLTokens.DIRECTED);

        Iterable<Vertex> vertices;
        if (normalize) {
            vertices = new ArrayList<Vertex>();
            for (Vertex v : graph.getVertices()) {
                ((Collection<Vertex>) vertices).add(v);
            }
            Collections.sort((List<Vertex>) vertices, new LexicographicalElementComparator());
        } else {
            vertices = graph.getVertices();
        }
        for (Vertex vertex : vertices) {
            serializer.startTag("", GraphMLTokens.NODE);
            serializer.attribute("", GraphMLTokens.ID, vertex.getId().toString());
            Collection<String> keys;
            if (normalize) {
                keys = new ArrayList<String>();
                keys.addAll(vertex.getPropertyKeys());
                Collections.sort((List<String>) keys);
            } else {
                keys = vertex.getPropertyKeys();
            }
            for (String key : keys) {
                serializer.startTag("", GraphMLTokens.DATA);
                serializer.attribute("", GraphMLTokens.KEY, key);
                Object value = vertex.getProperty(key);
                if (null != value) {
                    serializer.text(value.toString());
                }
                serializer.endTag("", GraphMLTokens.DATA);
            }
            serializer.endTag("", GraphMLTokens.NODE);
        }

        if (normalize) {
            List<Edge> edges = new ArrayList<Edge>();
            for (Vertex vertex : graph.getVertices()) {
                for (Edge edge : vertex.getEdges(Direction.OUT)) {
                    edges.add(edge);
                }
            }
            Collections.sort(edges, new LexicographicalElementComparator());

            for (Edge edge : edges) {
                serializer.startTag("", GraphMLTokens.EDGE);
                serializer.attribute("", GraphMLTokens.ID, edge.getId().toString());
                serializer.attribute("", GraphMLTokens.SOURCE, edge.getVertex(Direction.OUT).getId().toString());
                serializer.attribute("", GraphMLTokens.TARGET, edge.getVertex(Direction.IN).getId().toString());

                if (this.edgeLabelKey == null) {
                    // this will not comply with the graphml schema but is here so that the label is not
                    // mixed up with properties.
                    serializer.attribute("", GraphMLTokens.LABEL, edge.getLabel());
                } else {
                    serializer.startTag("", GraphMLTokens.DATA);
                    serializer.attribute("", GraphMLTokens.KEY, this.edgeLabelKey);
                    serializer.text(edge.getLabel());
                    serializer.endTag("", GraphMLTokens.DATA);
                }

                final List<String> keys = new ArrayList<String>();
                keys.addAll(edge.getPropertyKeys());
                Collections.sort(keys);

                for (String key : keys) {
                    serializer.startTag("", GraphMLTokens.DATA);
                    serializer.attribute("", GraphMLTokens.KEY, key);
                    Object value = edge.getProperty(key);
                    if (null != value) {
                        serializer.text(value.toString());
                    }
                    serializer.endTag("", GraphMLTokens.DATA);
                }
                serializer.endTag("", GraphMLTokens.EDGE);
            }
        } else {
            for (Vertex vertex : graph.getVertices()) {
                for (Edge edge : vertex.getEdges(Direction.OUT)) {
                    serializer.startTag("", GraphMLTokens.EDGE);
                    serializer.attribute("", GraphMLTokens.ID, edge.getId().toString());
                    serializer.attribute("", GraphMLTokens.SOURCE, edge.getVertex(Direction.OUT).getId().toString());
                    serializer.attribute("", GraphMLTokens.TARGET, edge.getVertex(Direction.IN).getId().toString());
                    serializer.attribute("", GraphMLTokens.LABEL, edge.getLabel());

                    for (String key : edge.getPropertyKeys()) {
                        serializer.startTag("", GraphMLTokens.DATA);
                        serializer.attribute("", GraphMLTokens.KEY, key);
                        Object value = edge.getProperty(key);
                        if (null != value) {
                            serializer.text(value.toString());
                        }
                        serializer.endTag("", GraphMLTokens.DATA);
                    }
                    serializer.endTag("", GraphMLTokens.EDGE);
                }
            }
        }

        serializer.endTag("", GraphMLTokens.GRAPH); // graph
        serializer.endTag("", GraphMLTokens.GRAPHML); // graphml
        serializer.endDocument();

        serializer.flush();
        graphMLOutputStream.flush();
        graphMLOutputStream.close();
    }

    /**
     * Write the data in a Graph to a GraphML OutputStream.
     *
     * @param graph               the Graph to pull the data from
     * @param graphMLOutputStream the GraphML OutputStream to write the Graph data to
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public static void outputGraph(final Graph graph, final OutputStream graphMLOutputStream) throws IOException {
        GraphMLWriter writer = new GraphMLWriter(graph);
        writer.outputGraph(graphMLOutputStream);
    }

    /**
     * Write the data in a Graph to a GraphML file.
     *
     * @param graph    the Graph to pull the data from
     * @param filename the name of the file write the Graph data (as GraphML) to
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public static void outputGraph(final Graph graph, final String filename) throws IOException {
        GraphMLWriter writer = new GraphMLWriter(graph);
        writer.outputGraph(filename);
    }

    /**
     * Write the data in a Graph to a GraphML file.
     *
     * @param graph          the Graph to pull the data from
     * @param filename       the name of the file write the Graph data (as GraphML) to
     * @param vertexKeyTypes a Map of the data types of the vertex keys
     * @param edgeKeyTypes   a Map of the data types of the edge keys
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public static void outputGraph(final Graph graph, final String filename,
                                   final Map<String, String> vertexKeyTypes, final Map<String, String> edgeKeyTypes) throws IOException {
        GraphMLWriter writer = new GraphMLWriter(graph);
        writer.setVertexKeyTypes(vertexKeyTypes);
        writer.setEdgeKeyTypes(edgeKeyTypes);
        writer.outputGraph(filename);
    }

    /**
     * Write the data in a Graph to a GraphML OutputStream.
     *
     * @param graph               the Graph to pull the data from
     * @param graphMLOutputStream the GraphML OutputStream to write the Graph data to
     * @param vertexKeyTypes      a Map of the data types of the vertex keys
     * @param edgeKeyTypes        a Map of the data types of the edge keys
     * @throws IOException thrown if there is an error generating the GraphML data
     */
    public static void outputGraph(final Graph graph, final OutputStream graphMLOutputStream,
                                   final Map<String, String> vertexKeyTypes, final Map<String, String> edgeKeyTypes) throws IOException {
        GraphMLWriter writer = new GraphMLWriter(graph);
        writer.setVertexKeyTypes(vertexKeyTypes);
        writer.setEdgeKeyTypes(edgeKeyTypes);
        writer.outputGraph(graphMLOutputStream);
    }

    private static String getStringType(final Object object) {
        if (object instanceof String) {
            return GraphMLTokens.STRING;
        } else if (object instanceof Integer) {
            return GraphMLTokens.INT;
        } else if (object instanceof Long) {
            return GraphMLTokens.LONG;
        } else if (object instanceof Float) {
            return GraphMLTokens.FLOAT;
        } else if (object instanceof Double) {
            return GraphMLTokens.DOUBLE;
        } else if (object instanceof Boolean) {
            return GraphMLTokens.BOOLEAN;
        } else {
            return GraphMLTokens.STRING;
        }
    }
}
