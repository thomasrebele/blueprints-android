BLUEPRINTS-ANDROID
==================

![Feature Image](https://github.com/wuman/blueprints-android/raw/master/doc/images/blueprints-android-logo.png)

Blueprints-android is an Android port/fork of Blueprints. It replaces the 
[Jettison](http://jettison.codehaus.org/) and [StAX](http://stax.codehaus.org/) 
libraries with native Android [JSON](http://developer.android.com/reference/org/json/package-summary.html) 
and [XML](http://developer.android.com/reference/android/util/Xml.html) parsers 
and serializers.

The current release version is 2.1.0, which is consistent with version 2.1.0 of 
Blueprints. The project is built using Maven and passes all the original unit
tests. However, I have only personally used the `blueprints-android-core` and
`blueprints-android-orient-graph` submodules in practice.


Including in Your Project
-------------------------

There are two ways to include blueprints-android in your projects:

1. You can download the released jar file in the [Downloads section](https://github.com/wuman/blueprints-android/downloads).
2. If you use Maven to build your project you can simply add a dependency to 
   the desired component of the library.

        <dependency>
            <groupId>com.wu-man</groupId>
            <artifactId>blueprints-android-*</artifactId>
            <version>2.1.0</version>
        </dependency>


Blueprints
----------

Blueprints is a [property graph model interface](http://github.com/tinkerpop/gremlin/wiki/Defining-a-Property-Graph). 
It provides implementations, test suites, and supporting extensions. Graph 
databases and frameworks that implement the Blueprints interfaces automatically 
support Blueprints-enabled applications. Likewise, Blueprints-enabled 
applications can plug-and-play different Blueprints-enabled graph backends.

* Implementations:
    * [TinkerGraph](http://wiki.github.com/tinkerpop/gremlin/tinkergraph in-memory graph)
    * [Neo4j](http://neo4j.org/ graph database)
        * [Neo4j High Availability](http://docs.neo4j.org/chunked/stable/ha-setup-tutorial.html) support
        * [Neo4j Batch Inserter](http://docs.neo4j.org/chunked/stable/indexing-batchinsert.html) support
    * [OrientDB](http://www.orientechnologies.com/) graph database
    * [DEX](http://www.sparsity-technologies.com/dex) graph database
    * [InfiniteGraph](http://www.infinitegraph.com/) 
      (available [here](http://wiki.infinitegraph.com/2.1/w/index.php?title=InfiniteGraph_Tinkerpop_Blueprints_Implementation))
    * [Titan](http://thinkaurelius.github.com/titan/) 
      (available [here](http://thinkaurelius.github.com/titan/))
    * [Rexster](http://rexster.tinkerpop.com) graph server
    * [Sesame 2.0](http://www.openrdf.org) compliant RDF stores
        * [MemoryStore](http://www.openrdf.org/doc/sesame2/users/ch08.html#d0e705), 
          [NativeStore](http://www.openrdf.org/doc/sesame2/users/ch08.html#d0e746), 
          [LinkedDataSail](http://code.google.com/p/ripple/wiki/LinkedDataSail), 
          [SPARQLRepository](http://www.openrdf.org/doc/sesame2/api/org/openrdf/repository/sparql/SPARQLRepository.html)
* Ouplementations:
    * [Java Universal/Graph](http://jung.sourceforge.net/) interface
    * [Sesame 2.0](http://www.openrdf.org) Sail interface
* Supporting utilities:
    * [GML](http://www.fim.uni-passau.de/en/fim/faculty/chairs/theoretische-informatik/projects.html) Reader/Writer utility
    * [GraphML](http://graphml.graphdrawing.org/ Reader/Writer) utility
    * [GraphSON](https://github.com/tinkerpop/blueprints/wiki/GraphSON-Reader-and-Writer-Library) Reader/Writer utility
    * `BatchGraph` wrapper
    * `ReadOnlyGraph` wrapper
    * `EventGraph` wrapper
    * `PartitionGraph` wrapper
    * `IdGraph` wrapper
    * Various helper utilities

The documentation for Blueprints can be found at this 
[location](http://blueprints.tinkerpop.com). Finally, please visit 
[TinkerPop](http://tinkerpop.com) for other software products.


Contribute
----------

If you would like to contribute code you can do so through GitHub by forking 
the repository and sending a pull request.


Developed By
------------

* Android porting contributor
    * David Wu - <david@wu-man.com> - [http://blog.wu-man.com](http://blog.wu-man.com)
* Original contributors to Blueprints
    * Marko A. Rodriguez - <marko@markorodriguez.com> - http://markorodriguez.com
    * Stephen Mallette - <spmva@genoprime.com> - http://stephen.genoprime.com
    * Joshua Shinavier - <josh@fortytwo.net> - http://fortytwo.net
    * Luca Garulli - <l.garulli@orientechnologies.com> - http://zion-city.blogspot.com
    * Darrick Wiebe - <darrick@innatesoftware.com> - http://github.com/pangloss
    * Matthias Broecheler - <me@matthiasb.com> - http://matthiasb.com

