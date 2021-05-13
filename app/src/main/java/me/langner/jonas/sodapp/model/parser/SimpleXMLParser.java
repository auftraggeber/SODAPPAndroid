package me.langner.jonas.sodapp.model.parser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Bildet die Superklasse für XML-Parser.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public abstract class SimpleXMLParser {

    private String xml;
    private Document document;

    /**
     * Speichert Daten für das Subobjekt.
     * @param xml Der XML-String.
     */
    public SimpleXMLParser(String xml) {
        this.xml = xml;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            this.document = builder.parse(new InputSource(new StringReader(xml)));

            start();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String getXml() {
        return xml;
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Wird zum Start aufgerufen.
     */
    public abstract void start();

    /**
     * Wird nach dem Ende aufgerufen.
     */
    public abstract void onFinish();

}
