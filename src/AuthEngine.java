import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthEngine {
    public static  AuthEngine AUTH_ENGINE = new AuthEngine();
    private byte[] inBuffer = new byte[40000];

    public static AuthEngine getInstance()
    {
        return  AUTH_ENGINE;
    }

    public String getStreamTag(String JID)
    {
        try
        {
            HashMap<String,String> streamAttributes = new HashMap<String,String>();
            streamAttributes.put("xmlns:stream","http://etherx.jabber.org/streams");
            streamAttributes.put("from",JID);
            streamAttributes.put("xmlns","jabber:client");
            streamAttributes.put("to",JID.split("@")[1]);
            streamAttributes.put("version","1.0");

            XmlDoc Stream = new XmlDoc("stream:stream",streamAttributes);
            return(Stream.getDocument().split("\\/>")[0]+">");

        }
        catch (Exception e)
        {

        }
        return null;
    }

    public String getAuthTag(String JID,String password,String mechanism)
    {
        HashMap<String,String> authAttributes = new HashMap<String, String>();
        authAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-sasl");
        authAttributes.put("mechanism",mechanism);

        String uid = '\0'+JID.split("@")[0]+'\0'+password; //Put ur password n jid;
        String Hash = new String(com.sun.xml.internal.messaging.saaj.util.Base64.encode(uid.getBytes()));



        XmlDoc auth = new XmlDoc("auth",authAttributes,Hash);
        return(auth.getDocument());
    }

    public String getStartTlsTag()
    {
        HashMap<String,String> tlsAttributes = new HashMap<String, String>();
        tlsAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-tls");

        XmlDoc tlsTag = new XmlDoc("starttls",tlsAttributes);

        return(tlsTag.getDocument());

    }

    public String getBindTag()
    {

        HashMap<String,String> bindAttributes = new HashMap<String, String>();
        bindAttributes.put("type","set");
        bindAttributes.put("id","tn281v37");

        XmlDoc bindTag = new XmlDoc("iq",bindAttributes);

        bindAttributes.clear();
        bindAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-bind");

        bindTag.addElement("bind",bindAttributes);
        return bindTag.getDocument();

    }

    public String getSessTag(String id,String to)
    {
        HashMap<String,String> sessAttributes = new HashMap<String, String>();
        sessAttributes.put("id",id);
        sessAttributes.put("to",to);
        sessAttributes.put("type","set");

        XmlDoc sessTag = new XmlDoc("iq",sessAttributes);

        sessAttributes.clear();
        sessAttributes.put("xmlns", "urn:ietf:params:xml:ns:xmpp-session");

        sessTag.addElement("session",sessAttributes);
        return sessTag.getDocument();
    }

    public  String getRosterReqTag(String JID,String id)
    {
        HashMap<String,String> reqRosterAttributes = new HashMap<String, String>();
        //reqRosterAttributes.put("from","JID");
        reqRosterAttributes.put("type","get");
        reqRosterAttributes.put("id",id);

        XmlDoc reqRosterTag = new XmlDoc("iq",reqRosterAttributes);

        reqRosterAttributes.clear();
        reqRosterAttributes.put("xmlns","jabber:iq:roster");

        reqRosterTag.addElement("query",reqRosterAttributes);
        return reqRosterTag.getDocument();
    }

    public boolean pingpongAuth(InputStream inStream, OutputStream outStream,String JID,String password)
    {
        try
        {
            BufferedInputStream binStream = new BufferedInputStream(inStream);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String Buff = "";

            PrintWriter pwOutStream = new PrintWriter(outStream,true);
            String srvInput;
            String toSrv;

            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);



            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Buff += srvInput;
                if(srvInput.contains("features"))
                    break;
            }

            //InputSource is = new InputSource(new StringReader(Buff));
            //Document doc = builder.parse(is);

            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("success"))
                    break;
            }

            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("jid"))
                    break;
            }

            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
           // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("params"))
                    break;
            }

            toSrv = getRosterReqTag(JID,JID.split("@")[0]+"_roster");
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);
            Buff = "";

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(binStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);
                Buff = Buff + srvInput;

                if(srvInput.contains("/iq"))
                    break;
            }

            System.out.println(Buff);
            /*srvInput = "";

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = srvInput + (new String(inBuffer));
                //System.out.println(srvInput);

                if(srvInput.contains("/iq>"))
                    break;
            }

            System.out.println("\n\n\n\n\n\n\n"+srvInput);

              */
            //XMLParsing code

            srvInput = null;

            PrintWriter fo = new PrintWriter("//home//tarang/testing3.txt");
            PrintWriter foo = new PrintWriter("//home//tarang/testing3.xml");
            fo.println(Buff);
            foo.println(Buff);

            //DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //DocumentBuilder builder = factory.newDocumentBuilder();
            //InputSource is = new InputSource(new StringReader(Buff));
            //Document doc = builder.parse(is);
              /*
            Element rootElem = doc.getDocumentElement();

            NodeList nodeList = rootElem.getChildNodes();

            for(int i=0;i<nodeList.getLength();i++)
            {
                Node currNode = nodeList.item(i);
                //System.out.println(currNode.getNodeName());
            }
                */

        }
        catch (Exception e)
        {

        }

        return false;
    }

    public boolean gtalkAuth(InputStream inStream, OutputStream outStream,String JID,String password)
    {
        try
        {
            PrintWriter pwOutStream = new PrintWriter(outStream,true);
            String srvInput;
            String toSrv;


            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("features"))
                    break;
            }



            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("success"))
                    break;
            }


            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("features"))
                    break;
            }

            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("jid"))
                    break;
            }

            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("params"))
                    break;
            }

            toSrv = getRosterReqTag(JID,JID.split("@")[0]+"_roster");
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);
           // srvInput="";

            while(inStream.read(inBuffer) > 0)
            {
                //srvInput = srvInput + (new String(inBuffer));
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("/iq>"))
                    break;
            }

            System.out.println(toSrv);
             srvInput="";

         /*   while(inStream.read(inBuffer) > 0)
            {
                srvInput = srvInput + (new String(inBuffer));
                //srvInput = new String(inBuffer);
                //System.out.println(srvInput);

                if(srvInput.contains("/iq>"))
                    break;
            }*/

            System.out.println("\n\n\n\n\n\n\n"+srvInput);


            //XMLParsing code

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //InputSource is = new InputSource(new StringReader(srvInput));
            //Document doc = builder.parse(is);


            PrintWriter fo = new PrintWriter("//home//tarang/testing2.txt");
            //FileOutputStream fo = new FileOutputStream("ServerSample.txt");
            fo.println(srvInput);
            System.out.println(srvInput);


           /*
            Element rootElem = doc.getDocumentElement();

            NodeList nodeList = rootElem.getChildNodes();

            for(int i=0;i<nodeList.getLength();i++)
            {
                Node currNode = nodeList.item(i);
                //System.out.println(currNode.getNodeName());
            }
             */

        }
        catch (Exception e)
        {
           System.out.println(e.toString());
        }

        return false;
    }

}


