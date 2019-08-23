package pxmpp;
import java.util.Scanner;
import org.jivesoftware.smack.XMPPException;
import static pxmpp.Logic.varConect;

/**
 * @author Silvestre
 */
public class PXMPP extends GLogic {

    public static void main(String[] args)throws XMPPException {
       Logic.ConfiConection();
        Scanner sn = new Scanner(System.in);
        String log="";
        boolean end = false;

        int option;
        if (Logic.Duser != ""){
        Logic.Duser= "Bienvenido "+Logic.Duser;
        }

       while(!end){
           System.out.println("");
           System.out.println("");
           System.out.println(" Management of account. " + Logic.Duser);
           System.out.println("1. sign up. ");
           System.out.println("2. log in. ");
           System.out.println("3. log out. ");
           System.out.println("4. Delete account. ");
           System.out.println(" Communication ");
           System.out.println("5. Show all Users.");
           System.out.println("6. Add users.");
           System.out.println("7. Details about one contact");
           System.out.println("8. Chat (peer to peer). ");
           System.out.println("9. Multi chat (group chat).");
           System.out.println("10. Set message of presence. ");
           System.out.println("11. Send/receive notifications. ");
           System.out.println("12. Send/receive files. ");
           System.out.println("13. Finish");
           System.out.println("Log of options: "+log+",");
            
           System.out.println("Select Option:");
           option = sn.nextInt();
           System.out.println("");
            log= log+""+Integer.toString(option)+"";
       
           
           
           switch(option){
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
                   Logic.FDeleteAccount();
                   break;
                   case 5:
                   Logic.ShowUsers();
                   break;
                   case 6:
                   Logic.addUsers();
                   break;
                   case 7:
                   Logic.ShowInformationContact();
                   break;
                   case 8:
                   Logic.sendMessage();
                   break;
                   case 9:
                   //Logic.JoinGroup();
                   Logic.sendPublicMessage();
                   break;
                   case 10:
                   Logic.SetMessageofpresence();
                   break;
                   case 11:
                   
                   break;
                   case 12:
                   
                   break;
                case 13:
                   end=true;
                   break;
                default:
                   System.out.println("options only between one to thirteen. ");
           }
            
       }
    }
    
}
