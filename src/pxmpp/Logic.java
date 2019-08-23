package pxmpp;

import java.util.Collection;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.VCard;

///the libraries make reference to smack 3.1.0 - 3.2.0
// thats libraries have this format org.jivesoftware.smack.. or org.jivesoftware.smackx..
/**
 * @author Silvestre
 */
public class Logic {

    public static ConnectionConfiguration varConfig = null; // var for the config global, contains server, port and securitymode
    public static XMPPConnection varConect = null; // var for the conection.
    public static final String ANSI_RED = "\u001B[31m"; // Color
    public static final String ANSI_BLACK = "\u001B[30m"; // Color
    public static String Duser = "";   // To management in the menu the IDuser
    protected static List<MultiUserChat> chatRooms = null; //to get the groups

    // This method add configuration data.
    //SASLAuthentication has not been activated in this method, because smack 3.1.0 has a fatal error with SASLAuthentication
    //This method only need conectionconfiguration and Set security mode to work
    public static void ConfiConection() {
        varConfig = new ConnectionConfiguration("alumchat.xyz", 5222);
        varConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        varConfig.setDebuggerEnabled(false);
        varConfig.setSendPresence(true);
    }

    //This method valid if the conection is on
    public static boolean Isonline() {
        return (varConect != null && varConect.isConnected());
    }

    //This method register account in server xmpp (Alumchat.xyz) if you wish change server xmpp only modify the method ConfiConection
    public static void FRegisterAccount() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Name: ");
            String lName = scan.nextLine();
            System.out.print("User: ");
            String lUserid = scan.nextLine();
            System.out.print("Password: ");
            String lPassword = scan.nextLine();

            varConect = new XMPPConnection(varConfig);
            varConect.connect();
            AccountManager lManager = varConect.getAccountManager(); // Account manager belongs to librarie org.jivesoftware.smack.AccountManager on smack 3.1.0
            lManager.createAccount(lUserid, lPassword);              // works for the management around of the user
            varConect.login(lUserid, lPassword);

            VCard vcard = new VCard();                        // VCard belongs to librarie org.jivesoftware.smackx.packet.VCard;
            vcard.load(varConect, lUserid + "@alumchat.xyz"); //works for the creating datas for the new user
            vcard.setFirstName(lName);
            vcard.setEmailHome(lUserid + "@alumchat.xyz");
            vcard.save(varConect);

            System.out.println("account successfully created.");

        } catch (Exception ex) {
            //ex.printStackTrace();// this capture the exception, but in this case print the follow mensaje.
            System.out.println("Server error or account already in server, more details: " + ex.getMessage());
        }

    }

    //This method login in server xmpp (Alumchat.xyz) if you wish change server xmpp only modify the method ConfiConection
    public static void FLogin() {
        try {

            Scanner scan = new Scanner(System.in);
            System.out.print("User id: ");
            String lUser = scan.nextLine();
            System.out.print("Password: ");
            String lPassword = scan.nextLine();
            varConect = new XMPPConnection(varConfig);
            varConect.connect();
            varConect.login(lUser, lPassword);
            System.out.println("Login sucessfully.");
            Duser = lUser;
        } catch (Exception ex) {
            //ex.printStackTrace();// this capture the exception, but in this case print the follow mensaje.
            System.out.println("Password incorrect or account does not exist, more details: " + ex.getMessage());

        }
    }

    //This method logout in server xmpp (Alumchat.xyz) if you wish change server xmpp only modify the method ConfiConection
    public static void FLogout() {
        try {
            varConect.disconnect();
            varConect = null;
            System.out.println("logout complete.");
            Duser = ""; // for the management message in the menu, review Method Menu
        } catch (Exception ex) {
            //ex.printStackTrace();// this capture the exception, but in this case print the follow mensaje.
            System.out.println("Server error or session is already over, more details: " + ex.getMessage());
        }
    }

    //This method delete Accounts in server xmpp (Alumchat.xyz) if you wish change server xmpp only modify the method ConfiConection
    public static void FDeleteAccount() {

        try {
            AccountManager lManager = varConect.getAccountManager();

            lManager.deleteAccount();
            varConect.disconnect();
            varConect = null;
            Duser = ""; // for the management message in the menu, review Method Menu
            System.out.println("Account deleted successfully");
        } catch (XMPPException ex) {
            System.out.println("Server error or account already deleted, more details: " + ex.getMessage());
        }
    }

    //This method shows your list of friends
    public static void ShowUsers() {
        Roster roster = varConect.getRoster(); // Roster belongs to librarie org.jivesoftware.smackx.Roster;
        Collection<RosterEntry> entries = roster.getEntries(); // Get the users in your friends list
        System.out.println("Name                        User                        ");
        for (RosterEntry entry : entries) {
            System.out.println(entry.getName() + "                        " + entry.getUser() + "                        ");
        }
    }

    //this method permits that you added new friends
    public static void addUsers() {
        Scanner scan = new Scanner(System.in);
        System.out.print("User id to add: ");
        String lUserid = scan.nextLine();

        try {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            Roster roster = varConect.getRoster();

            if (!roster.contains(lUserid)) {
                roster.createEntry(lUserid + "@alumchat.xyz", lUserid, new String[]{"Friends"});
            } else {
                System.out.println("User already in your list of friends.");
            }
        } catch (XMPPException ex) {
            System.out.println("Account does exist or already in your list of friends, more details: " + ex.getMessage());
        }
    }

    //This method shows an especific friend
    public static void ShowInformationContact() {
        try {
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
                if (entry.getUser().equals(lUserid)) {
                    System.out.println("User: " + entry.getUser());
                    System.out.println("State: " + entry.getStatus());
                    System.out.println("Presence: " + roster.getPresence(lUserid));
                }
            }
        } catch (Exception ex) {
            System.out.println("Server Error or user does not exist, more details: " + ex.getMessage());
        }
    }

    // this method add a message of presence in the server xmpp
    public static void SetMessageofpresence() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Message of presence: ");
        String lPresenceM = scan.nextLine();

        Presence lPresence = new Presence(Presence.Type.available); // Presence belongs to librarie org.jivesoftware.smack.packet.Presence;
        lPresence.setStatus(lPresenceM);
        varConect.sendPacket(lPresence);
    }

    // This method permits you chatting with your friends
    public static void sendMessage() {
        Scanner scan = new Scanner(System.in);
        System.out.print("User id to send message: ");
        String lUser = scan.nextLine() + "@alumchat.xyz";

        try {
            GLogic.ListenerMessage(varConect, lUser, lUser, null);//Listener Class is need here because if you don't have listener, you can't get the message
            Thread.sleep(50); // Only for the pause
            ChatManager chatManager = varConect.getChatManager();
            Chat chat = chatManager.createChat(lUser, new MessageListener() {
                @Override
                public void processMessage(Chat chat, Message msg) { // Here is the message incoming

                    String from = msg.getFrom();
                    String body = msg.getBody();
                    System.out.println("");
                    System.out.println(String.format("" + from + ": '%1$s'", body, "\\n"));
                    System.out.print("                             " + varConect.getUser() + ": ");
                }
            });
            String lMessage = "";

            while (lMessage.equalsIgnoreCase("adios") == false) { // this is the escape word and here is the outgoing message
                Thread.sleep(50);
                System.out.print("                             " + varConect.getUser() + ": ");
                lMessage = scan.nextLine();
                chat.sendMessage(lMessage);
            }
        } catch (Exception e) {
            System.out.print("Server error or acccount does not exist, more details: " + e.getMessage());
        }
    }

    //This method permits you can join in any group on the server XMPP
    public static void JoinGroup() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Name of Group: ");
        String lgroup = scan.nextLine();
        if (varConect.isAuthenticated() && lgroup != null) {
            chatRooms = new ArrayList<MultiUserChat>(); //MultiUserChat belongs to librarie  org.jivesoftware.smackx.muc.MultiUserChat;
            chatRooms.add(new MultiUserChat(varConect, lgroup));
            try {
                chatRooms.get(0).join(varConect.getUser());
            } catch (XMPPException e) {
                System.err.println("Server error or not exist group");
            }
        }
    }

    //This method permits send group messages
    public static void SendMessageGroup() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Name of Group: ");
        String lgroup = scan.nextLine();
        System.out.print("Message: ");
        String lMessage = scan.nextLine();
        MultiUserChat mucChat = (new MultiUserChat(varConect, lgroup));
        Message message = new Message(lgroup);
        //message.setStanzaId(stanzaId);
        message.setBody(lMessage);
        message.setType(Message.Type.groupchat);
        //message.addExtension(extension);
        try {
            mucChat.sendMessage(message);
            if (mucChat != null) {
                mucChat.sendMessage(message);
            }
        } catch (Exception ex) {

        }
    }
    //This is the menu, you can put it in the main if you wish.

    public static void Menu() {
        Logic.ConfiConection();
        Scanner sn = new Scanner(System.in);
        String log = "";
        boolean end = false;
        int option;
        while (!end) {
            System.out.println(" ");
            System.out.println(" ");
            if (Logic.Duser != "") { // in this line management the Iduser in the menu
                System.out.println("" + Emptyspace() + "Management of account. Welcome " + Logic.Duser); // EmptySpace() is equal to blank space, its only for the style
            } else {
                System.out.println("" + Emptyspace() + "Management of account.");
            }
            System.out.println("" + Emptyspace() + "1. sign up. ");
            System.out.println("" + Emptyspace() + "2. log in. ");
            if (Logic.Duser != "") {
                System.out.println("" + Emptyspace() + "3. log out. (" + Logic.Duser + ")");
            } else {
                System.out.println("" + Emptyspace() + "3. log out. ");
            }
            if (Logic.Duser != "") {
                System.out.println("" + Emptyspace() + "4. Delete account. (" + Logic.Duser + ")");
            } else {
                System.out.println("" + Emptyspace() + "4. Delete account. ");
            }
            System.out.println("" + Emptyspace() + "   Communication ");
            System.out.println("" + Emptyspace() + "5. Show all Users.");
            System.out.println("" + Emptyspace() + "6. Add users.");
            System.out.println("" + Emptyspace() + "7. Details about one contact");
            System.out.println("" + Emptyspace() + "8. Chat (peer to peer). ");
            System.out.println("" + Emptyspace() + "9. Multi chat (group chat).");
            System.out.println("" + Emptyspace() + "10. Set message of presence. ");
            System.out.println("" + Emptyspace() + "11. Send/receive notifications. ");
            System.out.println("" + Emptyspace() + "12. Send/receive files. ");
            System.out.println("" + Emptyspace() + "13. Finish");
            System.out.println("" + Emptyspace() + "Log of options: " + log + "");
            System.out.print("\033[31m Select Option:"); // \033[31m Is code for color red, in cmd does not work
            option = sn.nextInt();
            System.out.println("");
            log = log + "" + Integer.toString(option) + ","; // This line add a log for the option select for the user

            switch (option) {
                case 1:
                    Logic.FRegisterAccount();

                    break;
                case 2:
                    Logic.FLogin();
                    break;
                case 3:
                    Logic.FLogout();
                    break;
                case 4:
                    if (Logic.Duser != "") { //in this line added a validation if the user have a login successfully
                        Logic.FDeleteAccount();
                    } else {
                        System.out.println(ErrorMenu()); // Error menu equal to No sesion to start operation
                    }
                    break;
                case 5:
                    if (Logic.Duser != "") {
                        Logic.ShowUsers();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 6:
                    if (Logic.Duser != "") {
                        Logic.addUsers();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 7:
                    if (Logic.Duser != "") {
                        Logic.ShowInformationContact();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 8:
                    if (Logic.Duser != "") {
                        Logic.sendMessage();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 9:
                    //Logic.JoinGroup();
                    if (Logic.Duser != "") {
                        Logic.SendMessageGroup();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 10:

                    if (Logic.Duser != "") {
                        Logic.SetMessageofpresence();
                    } else {
                        System.out.println(ErrorMenu());
                    }
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    end = true;
                    break;
                default:
                    System.out.println("options only between one to thirteen. ");
            }
        }
    }

    //this method generates a validation of the other methos works correctly
    public static /*Class<?>*/ void Validationsesion(Class<?> Obj) { //Validation Methods
        if (Logic.Duser != "") {
            Obj.cast(Obj);
        } else {
            Obj.cast(ErrorMenu());
        }
    }

    //Message Error
    public static String ErrorMenu() {
        return "No sesion to start operation";
    }
    //Blank space

    public static String Emptyspace() {
        return "                                    ";
    }

}// End Logic
