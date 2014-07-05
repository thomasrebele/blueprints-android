BLUEPRINTS-ANDROID
==================

![Feature Image](https://github.com/wuman/blueprints-android/raw/master/doc/images/blueprints-android-logo.png)

NOTICE: this readme was not updated
NOTICE: add colt/colt-1.2.1.jar to maven with mvn install:install-file -Dfile=colt/colt-1.2.1.jar

Blueprints-android is an Android port/fork of 
[Blueprints](https://github.com/tinkerpop/blueprints/wiki). It replaces the 
[Jettison](http://jettison.codehaus.org/) and [StAX](http://stax.codehaus.org/) 
libraries with native Android [JSON](http://developer.android.com/reference/org/json/package-summary.html) 
and [XML](http://developer.android.com/reference/android/util/Xml.html) parsers 
and serializers.

The current release version is 2.1.0.x, which is in line with version 2.1.0 
of Blueprints. The project is built using Maven and passes all the original unit
tests. However, I have only personally used the `blueprints-android-core` and
`blueprints-android-orient-graph` submodules in practice. So use these libraries
at your own risk. If you wish to use blueprints-android in conjunction with
a database implementation other than OrientDB, you will have to include an
Android port of that database implementation yourself.


Including in Your Project
-------------------------

There are two ways to include the library in your projects:

1. You can download the released jar file in the [Downloads section](https://github.com/wuman/blueprints-android/downloads).
2. If you use Maven to build your project you can simply add a dependency to 
   the desired component of the library.

        <dependency>
            <groupId>com.wu-man</groupId>
            <artifactId>blueprints-*</artifactId>
            <version>2.1.0.2</version>
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


License
-------

    Copyright 2012, David Wu
    Copyright (c) 2009-2012, TinkerPop [http://tinkerpop.com]
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
        * Redistributions of source code must retain the above copyright
          notice, this list of conditions and the following disclaimer.
        * Redistributions in binary form must reproduce the above copyright
          notice, this list of conditions and the following disclaimer in the
          documentation and/or other materials provided with the distribution.
        * Neither the name of the TinkerPop nor the
          names of its contributors may be used to endorse or promote products
          derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
    DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
    ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/wuman/blueprints-android/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

