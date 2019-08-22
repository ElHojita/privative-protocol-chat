package pxmpp;
import java.util.Collection;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.VCard;
/**
 * @author Silvestre
 */
public class Logic  {
    public static ConnectionConfiguration varConfig = null;
    public static XMPPConnection varConect = null;   
    
    
    //begin Conection, configuration and status
        public static void ConfiConection ()
    {
        varConfig = new ConnectionConfiguration("alumchat.xyz",5222);
        varConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        varConfig.setDebuggerEnabled(false);
        varConfig.setSendPresence(true);
    }
    public static boolean Isonline()
    {
        return (varConect != null && varConect.isConnected());   
    }
    //end Conection, configuration and status
    //begin Adminstration, creation, login, logout and delete account
    public static void FRegisterAccount()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("Name: ");
            String lName = scan.nextLine();
            System.out.print("last Name: ");
            String llastname = scan.nextLine();
            System.out.print("User: ");
            String lUserid = scan.nextLine();
            System.out.print("Password: ");
            String lPassword = scan.nextLine();
            
            varConect = new XMPPConnection(varConfig);
            varConect.connect(); 
            AccountManager lManager = varConect.getAccountManager();
            lManager.createAccount(lUserid,lPassword);
           varConect.login(lUserid, lPassword);
            
            VCard vcard = new VCard();
            vcard.load(varConect, lUserid + "@alumchat.xyz");
            vcard.setFirstName(lName);
            vcard.setLastName(llastname);
            vcard.setEmailHome(lUserid + "@alumchat.xyz");
            vcard.save(varConect);
            
            System.out.println("account successfully created.");
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();// this capture the exception, but in this case print the follow mensaje.
              System.out.println("Server error or account already in server, more details: " + ex.getMessage());
        }
    }
    
      public static void FLogin()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("User id: ");
            String lUser = scan.nextLine();
            System.out.print("Password: ");
            String lPassword = scan.nextLine();
            varConect = new XMPPConnection(varConfig);
            varConect.connect(); 
            varConect.login(lUser, lPassword);
            System.out.println("Login sucessfully.");
        }
        catch(Exception ex)
        {
            System.out.println("Password incorrect or account does not exist, more details: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }
    
       public static void FLogout()
    { try   {
            varConect.disconnect();
            varConect = null;
            System.out.println("logout complete.");
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
            System.out.println("Server error or session is already over, more details: " + ex.getMessage());
        }
    }
     
     public static void FDeleteAccount()
    {
        try
        {
            AccountManager lManager = varConect.getAccountManager();
            lManager.deleteAccount();
            varConect.disconnect();
            varConect = null;
            
            System.out.println("Account deleted successfully");
        }
        catch(Exception ex)
        {
            System.out.println("Server error or account already deleted, more details: " + ex.getMessage());
            
        }
    }   
       
    //end Adminstration, creation, login, logout and delete account
    
     //begin  Show Users, AddUsers and ShowInformationContact
     
       public static void ShowUsers()
    {
        Roster roster = varConect.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        System.out.println("User \t\t Status");
        for (RosterEntry entry : entries) {
            System.out.println(entry.getName() +  " t\t " + entry.getStatus());
        }
    }
       
        public static void addUsers()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("User id to add: ");
        String lUserid = scan.nextLine();
        
        try
        {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            Roster roster = varConect.getRoster();

            if (!roster.contains(lUserid)) {
                roster.createEntry(lUserid + "@alumchat.xyz", lUserid, new String[] { "Friends" });
            } else {
                System.out.println("User already in your list of friends.");
            }
        }
        catch(XMPPException ex)
        {
            System.out.println("Account does exist or already in your list of friends, more details: " + ex.getMessage());
        }
    }
        
          public static void ShowInformationContact()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("User id : ");
            String lUserid = scan.nextLine() + "@alumchat.xyz";
            
            VCard card = new VCard();
            card.load(varConect, lUserid);
            System.out.println("First Name: " + card.getFirstName());
            System.out.println("Last Name: " + card.getLastName());
            System.out.println("Email: " + card.getEmailHome());
            
            Roster roster = varConect.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();
            for (RosterEntry entry : entries) {
                if (entry.getUser().equals(lUserid))
                {
                    System.out.println("User: " + entry.getUser());
                    System.out.println("State: " + entry.getStatus());
                    System.out.println("Presence: " + roster.getPresence(lUserid));
                }
            }
         }
        catch (Exception ex)
        {
                System.out.println("Server Error or user does not exist, more details: " + ex.getMessage());
        }
    }
     //end Show Users, AddUsers and ShowInformationContact
          
          //begin Messageofpresence and chats
          
           public static void SetMessageofpresence()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Message of presence: ");
        String lPresenceM = scan.nextLine();
        
        Presence lPresence = new Presence(Presence.Type.available);
        lPresence.setStatus(lPresenceM);
        varConect.sendPacket(lPresence);
    }
          //end Messageofpresence and chats
    
    
}// End Logic
