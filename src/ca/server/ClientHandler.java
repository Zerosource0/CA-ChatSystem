/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Observable;
import ca.shared.ProtocolStrings;

/**
 *
 * @author Adam
 */
public class ClientHandler extends Observable implements Runnable
{
    Scanner input;
    PrintWriter writer;
    private Socket socket;
    private Observer o;
    private String clientName;
    ServerMessages sMsgs;
    
    public String getClientName() {
        return clientName;
    }
    
    public ClientHandler (Socket s, Observer o) throws IOException
    {
        this.o=o;
        this.socket=s;
        sMsgs = new ServerMessages(Server.getClientList());
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        addObserver(o);
        
    }
    @Override
    public void run() 
    {
        String temp[] = input.nextLine().split("USER#");
        clientName = temp[1];
        Server.getSysMsg().sendList();
        System.out.println("first message recieved");
        String message = input.nextLine();
    Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ",message));
    while (!message.equals(ProtocolStrings.STOP)) {
        setChanged();
        notifyObservers(message);
        clearChanged();
      Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ",message.toUpperCase()));
      message = input.nextLine(); 
    }
    writer.println(ProtocolStrings.STOP);
    try {
            socket.close();
            remove();
            Server.getSysMsg().sendList();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    Logger.getLogger(Server.class.getName()).log(Level.INFO, "Closed a Connection");
    }
    public void remove()
    {
        Server.removeClient(this);
    }
    public void send(String msg)
    {
        writer.println(msg);
        
    }

    
}
