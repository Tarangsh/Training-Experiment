
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlDoc {

    static DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    static DocumentBuilder docBuilder;

    static TransformerFactory transformerFactory = TransformerFactory.newInstance();
    static Transformer transformer;

    static
    {
        try
        {
            docBuilder = docFactory.newDocumentBuilder();
            transformer = transformerFactory.newTransformer();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


    Document doc;

    public static Document Parse(String repDoc)
    {
        Document srvDoc = null;
        try
        {
             srvDoc = docBuilder.parse(new InputSource(new StringReader( repDoc ) ));
        }
        catch (Exception e)
        {

        }
        return srvDoc;
    }

    public XmlDoc(String root, HashMap<String, String> attList)
    {
        doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(root);
        doc.appendChild(rootElement);
        Attr currAtt;

        for(String attribute:attList.keySet())
        {
            currAtt = doc.createAttribute(attribute);
            currAtt.setValue(attList.get(attribute));
            rootElement.setAttributeNode(currAtt);
        }
    }

    public XmlDoc(String root, HashMap<String, String> attList, String content)
    {
        doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(root);
        doc.appendChild(rootElement);
        Attr currAtt;

        for(String attribute:attList.keySet())
        {
            currAtt = doc.createAttribute(attribute);
            currAtt.setValue(attList.get(attribute));
            rootElement.setAttributeNode(currAtt);
        }

        rootElement.setTextContent(content);
    }

    public void addElement(String element,HashMap<String,String> attList)
    {
        Element rootElement = doc.getDocumentElement();
        Element currElement = doc.createElement(element);
        rootElement.appendChild(currElement);
        Attr currAtt;

        for(String attribute:attList.keySet())
        {
            currAtt = doc.createAttribute(attribute);
            currAtt.setValue(attList.get(attribute));
            currElement.setAttributeNode(currAtt);
        }
    }

    public String getDocument()
    {
        String docStr;

        DOMSource source = new DOMSource(doc);
        StringWriter stringWriter = new StringWriter();

        try
        {
            transformer.transform(source, new StreamResult(stringWriter));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        docStr = stringWriter.toString();

        //return docStr;
        return (docStr.split("\\?>")[1]);
    }


}
