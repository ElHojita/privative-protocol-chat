package pxmpp;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
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
            String lNombre = scan.nextLine();
            System.out.print("last Name: ");
            String lApellido = scan.nextLine();
            System.out.print("User: ");
            String lUsuario = scan.nextLine();
            System.out.print("Password: ");
            String lClave = scan.nextLine();
            
            varConect = new XMPPConnection(varConfig);
            varConect.connect(); 
            AccountManager lManager = varConect.getAccountManager();
            lManager.createAccount(lUsuario,lClave);
           varConect.login(lUsuario, lClave);
            
            VCard vcard = new VCard();
            vcard.load(varConect, lUsuario + "@alumchat.xyz");
            vcard.setFirstName(lNombre);
            vcard.setLastName(lApellido);
            vcard.setEmailHome(lUsuario + "@alumchat.xyz");
            vcard.save(varConect);
            
            System.out.println("account successfully created.");
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();// this capture the exception, but in this case print the follow mensaje.
              System.out.println("Server error or account already in server.");
        }
    }
    
      public static void FLogin()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("User id: ");
            String lUsuario = scan.nextLine();
            System.out.print("Password: ");
            String lClave = scan.nextLine();
            varConect = new XMPPConnection(varConfig);
            varConect.connect(); 
            varConect.login(lUsuario, lClave);
            System.out.println("Login sucessfully.");
        }
        catch(Exception ex)
        {
            System.out.println("Password incorrect or account does not exist.");
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
            System.out.println("Server error or session is already over.");
        }
    }
    //end Adminstration, creation, login, logout and delete account
    
     
    
    
}// End Logic
