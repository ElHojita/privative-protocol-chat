/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pxmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

/**
 *
 * @author Silvestre
 */
public class Logic {
    public static ConnectionConfiguration varConfig = null;
    public static XMPPConnection varConect = null;   
    
    
    
    
    
        public static void ConfiConection()
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
    
    
}
