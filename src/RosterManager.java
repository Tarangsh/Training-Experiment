

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 15/11/12
 * Time: 6:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RosterManager {
    private ArrayList<Contact> contactsList = new ArrayList<Contact>();

    public static RosterManager ROSTER_MANAGER = new RosterManager();

    public static RosterManager getInstance() {
        return ROSTER_MANAGER;
    }

    public void insert(String JID, String acctID, String presence, String image)
    {
        Contact contact = new Contact();
        contact.setJID(JID);
        contact.setAcctID(acctID);
        contact.setPresence(presence);
        contact.setImage(image);

        contactsList.add(contact);
    }

    public void reset() {
        contactsList.clear();
    }

    public void pushRosterPacket(String packet, String acctID)
    {
        //insert in the list from here
    }

    public void updatePresence(String jid)
    {

    }

}
