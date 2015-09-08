/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Observable implements Runnable {

    Socket socket;
    InetAddress serverAddress;
    Scanner input;
    PrintWriter output;
    String ip;
    int port;
    String userName;
    private ArrayList<String> users;
    MessageProcessor msgProcessor;
    public Settings settings;

    public Client() {

        settings = new Settings(this);
        settings.loadSettings();
        msgProcessor = new MessageProcessor(this);
        users = new ArrayList();
    }

    public ArrayList<ServerInfo> getServers() {
        return settings.getServers();
    }

    public void setServers(ArrayList<ServerInfo> servers) {
        settings.setServers(servers);
    }

    public void loadSettings() {
        settings.loadSettings();
    }

    public void saveSettings() {
        settings.saveSettings();
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        send("USER#" + userName);
    }

    public void send(String msg) {
        output.println(msg);
    }

    @Override
    public void run() {
        String msg = input.nextLine(); //blocking call
        while (!msg.equals("STOP#")) {
            setChanged();
            notifyObservers(msg);
            msg = input.nextLine();
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
