/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.server;

import ca.client.Client;
import java.util.ArrayList;

/**
 *
 * @author marcj_000
 */
public class MessageProcessor {

    ClientHandler origin;

    public MessageProcessor(ClientHandler origin) {
        this.origin = origin;
    }

    public void process(String message) {
        //if message starts with MSG#, remove MSG#-tag. Find cleints and send message to them.
        if (message.startsWith("MSG#")||message.startsWith("STOP#")) {
            message = message.replace("MSG#", "");
            findUsers(message);
        }else origin.send("Wrong syntax!");
        
    }
    //TODO, MAKE SURE THAT PERSONS CANT SEND MESSAGES TO A RECIEPIENT MORE THAN ONCE! EX. MSG#USER1,USER1#MESSAGE
    private void findUsers(String message) {
        //Message should look like user,user#Message
        //first split at # to get user,user

        String[] split = message.split("#");
        //split[0] = user,user & split[1]message
        String users = split[0];
        message = split[1];
        //if message contains more than one #, it must be part of the message.
        //The Split[] will therefore have index greater than 2.
        //And we will need to add the # back into the message.
        if (split.length > 2) {
            for (int i = 1; i < split.length; i++) {
                message = message.concat("#" + split[i]);
            }
        }

        //split at users at "," to get individual users.
        String[] usersSplit = new String[1];
        usersSplit[0] = users;
        if (users.contains(",")) {
            usersSplit = users.split(",");
        }

        
        
        //Get users
        ArrayList<ClientHandler> clientsInList = Server.getClientList();
        ArrayList<ClientHandler> clientRecievers = new ArrayList(); //make empty array to add reciepient in.
        //compare user(s) in String[] userSplit to clientHanlder.getCleintName();
        for (String user : usersSplit) {
            for (ClientHandler client : clientsInList) {
                if (user.equals(client.getClientName())) {
                    clientRecievers.add(client);
                }
            }
        }
        if(clientRecievers.contains(origin)){
            clientRecievers.remove(origin);
            origin.send("Info: You can't message yourself.");
        }
        Server.sendToUsers(clientRecievers, origin.getClientName()+": "+message); //send messages to users.
    }
}
