package pxmpp;
import java.util.Scanner;
/**
 * @author Silvestre
 */
public class PXMPP {

    public static void main(String[] args) {
        
      Scanner sn = new Scanner(System.in);
       boolean end = false;
       int option; //Guardaremos la opcion del usuario
        
       while(!end){
            
           System.out.println("1. sign up 1");
           System.out.println("2. log in 2");
           System.out.println("3. log out 3");
           System.out.println("4. Salir");
            
           System.out.println("Select Option");
           option = sn.nextInt();
            
           switch(option){
               case 1:
                   System.out.println("Has seleccionado la opcion 1");
                   break;
               case 2:
                   System.out.println("Has seleccionado la opcion 2");
                   break;
                case 3:
                   System.out.println("Has seleccionado la opcion 3");
                   break;
                case 4:
                   end=true;
                   break;
                default:
                   System.out.println("Solo números entre 1 y 4");
           }
            
       }
    }
    
}
