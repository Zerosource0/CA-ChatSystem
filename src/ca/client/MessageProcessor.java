/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.client;

import java.util.ArrayList;

/**
 *
 * @author marcj_000
 */
public class MessageProcessor {

    Client client;

    public MessageProcessor(Client client) {
        this.client = client;
    }

    public String process(String msg) {
        System.out.println("RECIEVED: " +msg);
        
        if (msg.startsWith("MSG#")) {
            System.out.println("Contains MSG#");
            
            msg = msg.replace("MSG#", "");
            String[] split = msg.split("#");
            try {
                return new String(split[0] + ": " + split[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("outofBounds"+ e.getStackTrace());
                return "";
            }
            

        }

        //Check if system message   
        //Check if system message is clientList
        if (msg.contains("USERLIST#")) {
            String[] users = msg.split("USERLIST#");
            users = users[1].split(",");
            addUsersToList(users);
            msg = "";
        }

        if (msg.contains("STOP#")) {
            msg = "";
        }
        //else return message
        return msg;
    }

    public void addUsersToList(String[] users) {
        client.getUsers().clear();
        for (String user : users) {
            client.getUsers().add(user);
        }
        System.out.println(client.getUsers().toString());
    }
}
