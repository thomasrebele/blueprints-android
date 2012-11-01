package com.tinkerpop.blueprints.util.io.graphml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class AndroidXmlFactory {

    public static XmlPullParser parser;
    public static XmlSerializer serializer;

    private AndroidXmlFactory() {
    }

    public static XmlPullParser newPullParser() {
        if (parser == null) {
            parser = Xml.newPullParser();
        }
        return parser;
    }

    public static XmlSerializer newSerializer() {
        if (serializer == null) {
            serializer = Xml.newSerializer();
        }
        return serializer;
    }

}
