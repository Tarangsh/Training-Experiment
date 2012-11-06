import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 31/10/12
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Primary {

    static String initiate_conn="<?xml version=\"1.0\"?>\n\r<stream:stream to=\"google.com\"\n\rversion=\"1.0\"\n\rxmlns=\"jabber:client\"\n\rxmlns:stream=\"http://etherx.jabber.org/streams\">\n";
    static String start_tls="<starttls xmlns=\"urn:ietf:params:xml:ns:xmpp-tls\"/>";


    public Primary()
    {

    }

    public String createDoc()
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            org.w3c.dom.Element rootElement = doc.createElement("stream:stream");
            doc.appendChild(rootElement);

            System.out.println(doc);

           // Attr attr = doc.createAttribute("from");
            //attr.setValue("cadet.tarang@gmail.com");
            //rootElement.setAttributeNode(attr);

            Attr attr = doc.createAttribute("to");
            attr.setValue("gmail.com");
            rootElement.setAttributeNode(attr);

            attr = doc.createAttribute("xmlns");
            attr.setValue("jabber:client");
            rootElement.setAttributeNode(attr);

            attr = doc.createAttribute("xmlns:stream");
            attr.setValue("http://etherx.jabber.org/streams");
            rootElement.setAttributeNode(attr);





            attr = doc.createAttribute("version");
            attr.setValue("1.0");
            rootElement.setAttributeNode(attr);


            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            System.out.println(doc);

            StringWriter stringWriter = new StringWriter();
            StreamResult result =  new StreamResult(new File("//home//tarang/testing.xml"));
            transformer.transform(source, new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString();

            System.out.println(strFileContent);
            System.out.println(doc.getDocumentElement().toString());

            System.out.println("Done");

            return(strFileContent);
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }

        return(null);
    }

    public String getTLS()
    {
        try{

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            org.w3c.dom.Element rootElement = doc.createElement("starttls");
            doc.appendChild(rootElement);

            Attr attr = doc.createAttribute("xmlns");
            attr.setValue("urn:ietf:params:xml:ns:xmpp-tls");
            rootElement.setAttributeNode(attr);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter stringWriter = new StringWriter();
            //StreamResult result =  new StreamResult(new File("//home//tarang/testing.xml"));
            transformer.transform(source, new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString();

            return(strFileContent);

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return null;
    }


    public String getIq()
    {
       try{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        //root elements
        Document doc = docBuilder.newDocument();

        org.w3c.dom.Element rootElement = doc.createElement("iq");
        doc.appendChild(rootElement);

           Attr attr = doc.createAttribute("to");
           attr.setValue("gmail.com");
           rootElement.setAttributeNode(attr);

           attr = doc.createAttribute("type");
           attr.setValue("set");
           rootElement.setAttributeNode(attr);

           attr = doc.createAttribute("id");
           attr.setValue("sess_1");
           rootElement.setAttributeNode(attr);

           Element Sess = doc.createElement("starttls");

           attr = doc.createAttribute("xmlns");
           attr.setValue("urn:ietf:params:xml:ns:xmpp-tls");
           Sess.setAttributeNode(attr);

           rootElement.appendChild(Sess);

           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           DOMSource source = new DOMSource(doc);

           StringWriter stringWriter = new StringWriter();
           //StreamResult result =  new StreamResult(new File("//home//tarang/testing.xml"));
           transformer.transform(source, new StreamResult(stringWriter));
           String strFileContent = stringWriter.toString();

           return(strFileContent);


       }
       catch(Exception e)
       {
           System.out.println(e.toString());
       }

       return null;
    }

    public String getAuth()
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            org.w3c.dom.Element rootElement = doc.createElement("auth");
            doc.appendChild(rootElement);

            Attr attr = doc.createAttribute("xmlns");
            attr.setValue("urn:ietf:params:xml:ns:xmpp-sasl");
            rootElement.setAttributeNode(attr);

            attr = doc.createAttribute("mechanism");
            attr.setValue("PLAIN");
            rootElement.setAttributeNode(attr);

            //rootElement.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter stringWriter = new StringWriter();
            //StreamResult result =  new StreamResult(new File("//home//tarang/testing.xml"));
            transformer.transform(source, new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString();

            return(strFileContent);

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return null;
    }

    public void displayDoc(String f1)
    {
       try{

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                Document XMLdoc = docBuilder.parse(f1);

                Element E = XMLdoc.getDocumentElement();
                System.out.println(E.toString());
                //Attr atr = E.


       }
       catch(Exception e)
       {
           System.out.println(e.toString());
       }
    }

    public void XMPPConnect()
    {

        try{
                Socket connection = new Socket("talk.google.com", 5222);


                 DataInputStream input = new DataInputStream(connection.getInputStream());
                 BufferedReader d = new BufferedReader(new InputStreamReader(input));
                 OutputStream to_server = null;
                 String responseLine;

                 to_server = connection.getOutputStream();
                 to_server.write(initiate_conn.getBytes());
                 responseLine = d.readLine();
                 System.out.println("Server: " + responseLine);
                 to_server.write(start_tls.getBytes());
                 responseLine = d.readLine();
                 System.out.println("Server: " + responseLine);

        }
        catch(Exception e)
        {
                 System.out.println(e.toString());
        }
    }

    public Socket getSSlSock(String serverURL, int portNumber)  throws Exception
    {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket socket = sslSocketFactory.createSocket(serverURL, portNumber);
        socket.setSoTimeout(0);
        socket.setKeepAlive(true);

        return socket;

    }

    public void facebookAuth()
    {
        try{
        Socket sock1 = new Socket("chat.facebook.com",5222);
        System.out.println(sock1.toString());


        DataInputStream input = new DataInputStream(sock1.getInputStream());
        BufferedReader d = new BufferedReader(new InputStreamReader(input));
        OutputStream to_server = null;
        String responseLine;



            to_server = sock1.getOutputStream();

            String Stream =  "<stream:stream\n"+"xmlns:stream=\"http://etherx.jabber.org/streams\"\n" +"version=\"1.0\" xmlns=\"jabber:client\" to=\"chat.facebook.com\"\n"
            +"xml:lang=\"en\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"/>";




            System.out.println(Stream);
            to_server.write(Stream.getBytes());

            String I = d.readLine();

            System.out.println(I);


           // String START_TLS = "<starttls xmlns=\"urn:ietf:params:xml:ns:xmpp-tls\"/>";
            String START_TLS = "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" mechanism=\"DIGEST-MD5\">AGR1bW15LmFuZHJvaWQuY2hhdABkdW1teWFuZA=</auth>";

            System.out.println(START_TLS);
            to_server.write(START_TLS.getBytes());

            I = d.readLine();

            System.out.println(I);

            I = d.readLine();

            System.out.println(I);

            /*String Auth = "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"\n"+"mechanism=\"X-FACEBOOK-PLATFORM\"></auth>";

            System.out.println(Auth);
            to_server.write(Auth.getBytes());

            I = d.readLine();

            System.out.println(I);
              */
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public void pingpongAuth()
    {
        try
        {
            String A = "10.10.1.31";
            InetAddress PingpongAddr = InetAddress.getByName("10.10.1.31");
            Socket sock1 = new Socket(PingpongAddr,5222);
            //Socket sock1 = new Socket("talk.google.com",5222);
            System.out.println(sock1.toString());

            byte[] inBuffer = new byte[2000];


            PrintWriter pw = new PrintWriter(sock1.getOutputStream(),true);

            DataInputStream input = new DataInputStream(sock1.getInputStream());
            BufferedReader d = new BufferedReader(new InputStreamReader(input));
            OutputStream to_server = null;
            String responseLine;



            //to_server = sock1.getOutputStream();

            //String stream =  "<stream:stream\n"+"xmlns:stream=\"http://etherx.jabber.org/streams\"\n" +"version=\"1.0\" xmlns=\"jabber:client\" to=\"chat.facebook.com\"\n"
              //      +"xml:lang=\"en\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"/>";

            String stream = "<stream:stream xmlns:stream=\"http://etherx.jabber.org/streams\" from=\"tarang.s@directi.com\" xmlns=\"jabber:client\" to=\"directi.com\" version=\"1.0\">";



            pw.println(stream);
            System.out.println(stream);

            input.read(inBuffer);
            String I = new String(inBuffer);
            System.out.println(I);

            input.read(inBuffer);
            String J = new String(inBuffer);
            System.out.println(J);
            System.out.print("\n\n\n\n");

            String uid = '\0'+"tarang.s"+'\0'+"wrongpwd"; //Put ur password n jid;
            String Hash = new String(Base64.encode(uid.getBytes()));

            String D =  "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" mechanism=\"PLAIN\">"+Hash+"</auth>";

            System.out.println(D);
            pw.println(D);

            input.read(inBuffer);
            J = new String(inBuffer);
            System.out.println(J);
            System.out.print("\n\n\n\n");

            D =  "<iq type=\"set\" id=\"tn281v37\"><bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\"/></iq>";

            System.out.println(D);
            pw.println(D);

            input.read(inBuffer);
            J = new String(inBuffer);
            System.out.println(J);
            System.out.print("\n\n\n\n");

            D =  "<iq id=\"sess_1\" to=\"directi.com\" type=\"set\"><session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"/></iq>";

            System.out.println(D);
            pw.println(D);

            input.read(inBuffer);
            J = new String(inBuffer);
            System.out.println(J);
            System.out.print("\n\n\n\n");

            D =  "<message to=\'pawan.m@directi.com\'>\n" + "<body>Hello</body>\n" + "</message>";
            System.out.println(D);
            pw.println(D);

            input.read(inBuffer);
            J = new String(inBuffer);
            System.out.println(J);
            System.out.print("\n\n\n\n");



        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


    public static void main(String[] args)
    {
        try{

            Primary P = new Primary();
            //P.XMPPConnect();
           // Socket sock1 = new Socket("talk.google.com",5222);
           // Socket sock1 = new Socket("10.10.1.31",5222);

            // Socket sock1 = P.getSSlSock("talk.google.com",443);

          //  P.facebookAuth();


            //Base64.encodeBytes(authString.getBytes())

            P.pingpongAuth();         /*
            //Socket sock1 = new Socket("chat.facebook.com",5222);
            System.out.println(sock1.toString());


            DataInputStream input = new DataInputStream(sock1.getInputStream());
            BufferedReader d = new BufferedReader(new InputStreamReader(input));
            OutputStream to_server = null;
            String responseLine;

            to_server = sock1.getOutputStream();


             //Primary P = new Primary();

             //String D = P.createDoc();

             //String D = "<stream:stream xmlns:stream=\"http://etherx.jabber.org/streams\"  xmlns=\"jabber:client\" to=\"facebook.com\" version=\"1.0\">";
             String D = "<stream:stream xmlns:stream=\"http://etherx.jabber.org/streams\"  xmlns=\"jabber:client\" to=\"gmail.com\" version=\"1.0\">";
             //String D = "<stream:stream xmlns:stream=\"http://etherx.jabber.org/streams\"  xmlns=\"jabber:client\" to=\"directi.com\" version=\"1.0\">";



            System.out.println(D);

             to_server.write(D.getBytes());

             String I = d.readLine();

             System.out.println(I);


            D =  "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" mechanism=\"PLAIN\">=</auth>";

            to_server.write(D.getBytes());

            I = d.readLine();

            System.out.println(I);

            sock1.close();

            //String Session = P.getAuth();

           // String Session = "<auth xmlns=\'urn:ietf:params:xml:ns:xmpp-sasl\'" +"\n" + "mechanism='EXTERNAL'>=</auth>";
            //String Session = "<starttls xmlns=\"urn:ietf:params:xml:ns:xmpp-tls\"/>";
           // String Session = "<starttls/>";

            //System.out.println(Session);

            //to_server.write(Session.getBytes());

            //I = d.readLine();

            //System.out.println(I);


            //while(I == null)
            //{
              //  to_server.write(D.getBytes());
                //System.out.println(D);

                //I = d.readLine();

               // System.out.println(I);



             //   System.out.println(Session);

               // to_server.write(Session.getBytes());

                //I = d.readLine();
               // System.out.println(I);
           // }

                                                           */

            System.out.println("Transaction complete.....");
           }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

    }
}























