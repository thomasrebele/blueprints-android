package com.tinkerpop.blueprints;

import java.util.Set;

/**
 * A KeyIndexableGraph is a graph that supports basic index functionality around the key/value pairs of the elements of the graph.
 * By creating key indices for a particular property key, that key is indexed on all the elements of the graph.
 * This has ramifications for quick lookups on methods like getVertices(String, Object) and getEdges(String, Object).
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface KeyIndexableGraph extends Graph {

    /**
     * Remove an automatic indexing structure associated with indexing provided key for element class.
     *
     * @param key          the key to drop the index for
     * @param elementClass the element class that the index is for
     * @param <T>          the element class specification
     */
    public <T extends Element> void dropKeyIndex(String key, Class<T> elementClass);

    /**
     * Create an automatic indexing structure for indexing provided key for element class.
     *
     * @param key          the key to create the index for
     * @param elementClass the element class that the index is for
     * @param <T>          the element class specification
     */
    public <T extends Element> void createKeyIndex(String key, Class<T> elementClass);

    /**
     * Return all the index keys associated with a particular element class.
     *
     * @param elementClass the element class that the index is for
     * @param <T>          the element class specification
     * @return the indexed keys as a Set
     */
    public <T extends Element> Set<String> getIndexedKeys(Class<T> elementClass);

    /**
     * Return an iterable to all the vertices in the graph that have a particular key/value property.
     * If a particular key index is not provided, then an InvalidStateException should be thrown.
     *
     * @param key   the key of vertex
     * @param value the value of the vertex
     * @return an iterable of vertices with provided key and value
     */
    public Iterable<Vertex> getVertices(String key, Object value);

    /**
     * Return an iterable to all the edges in the graph that have a particular key/value property.
     * If a particular key index is not provided, then an InvalidStateException should be thrown.
     *
     * @param key   the key of the edge
     * @param value the value of the edge
     * @return an iterable of edges with provided key and value
     */
    public Iterable<Edge> getEdges(String key, Object value);
}
