/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author williambech
 */
public class NonGUIClient implements Observer {

    String recievedMsg;

    public void mainRun() {
        Client client = new Client();
        Thread thread = new Thread(client);
        Scanner scanIn = new Scanner(System.in);
        try {
            System.out.println("Enter your name: ");
            String tmpUser = scanIn.nextLine();
            client.setUserName(tmpUser);
            client.addObserver(this);

            System.out.println("Enter server IP:");
            client.setIp(scanIn.nextLine());
            client.connect(client.getIp(), 9090);
            System.out.println("connected");

            thread.start();

        } catch (IOException e) {
            Logger.getLogger(NonGUIClient.class.getName()).log(Level.SEVERE, null, e);
        }

        String msg = "";
        while (!msg.equals("STOP#")) {
            msg = scanIn.nextLine();
            client.send(msg);
        }
        scanIn.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        recievedMsg = (String) arg;
        System.out.println(recievedMsg);
    }

}
