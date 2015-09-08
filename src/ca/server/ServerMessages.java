/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcj_000
 */
public class ServerMessages {
    
    ArrayList<ClientHandler> clientList;

    public ServerMessages(ArrayList<ClientHandler> clientList) {
        this.clientList = clientList;
    }
    
    public void sendList(){
        
        String msg = "USERLIST#";
        
        for (ClientHandler client : clientList) {
            
            msg = msg.concat(client.getClientName()+",");
            
        }
        System.out.println("SOUT: SENDING CLIENTLIST: " +msg);
        Server.sendAll(msg);
    }
    
}
