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
       boolean end = false;
       int option; 
        
       while(!end){
          
           System.out.println(" Management of account. ");
           System.out.println("1. sign up. ");
           System.out.println("2. log in. ");
           System.out.println("3. log out. ");
           System.out.println("4. Delete account. ");
           System.out.println(" Comunicación ");
           System.out.println("5. Mostrar todos los usuarios/contactos y su estado.");
           System.out.println("6.Agregar un usuario a los contactos.");
           System.out.println("7. Mostrar detalles de contacto de un usuario");
           System.out.println("8. Comunicación 1 a 1 con cualquier usuario/contacto. ");
           System.out.println("9. Participar en conversaciones grupales.");
           System.out.println("10. Definir mensaje de presencia. ");
           System.out.println("11. Enviar/recibir notificaciones. ");
           System.out.println("12. Enviar/recibir archivos. ");
           System.out.println("13. Finish");
            
           System.out.println("Select Option:");
           option = sn.nextInt();
            
       
           
           
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
                       Logic.sendPublicMessage("Prueba Enviar mensaje","Groupo1");
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
