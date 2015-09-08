/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;
/**
 *
 * @author marcj_000
 */


public class Server implements Observer{
    private static final Properties properties = Utils.initProperties("server.properties");
    ServerSocket ss;
    private boolean isRunning=true;
    private static List <ClientThread> clientList = new ArrayList<ClientThread>() ;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile,Server.class.getName());
        new Server().runServer();

        
    }
    public static void removeClient (ClientThread ct)
    {
        clientList.remove(ct);
        
    }
    
    private void runServer ()
    {
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip=properties.getProperty("serverIp");
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Sever started. Listening on: "+port+", bound to: "+ip);
        
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress(ip, port));
            do 
            {
                Socket socket = ss.accept();
                Logger.getLogger(Server.class.getName()).log(Level.INFO, "Connected to a client");
                handleClient(socket);
            } while (isRunning);
        } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
               Utils.closeLogger(Server.class.getName());
        }
    }
    private void handleClient(Socket s) throws IOException
    {
        ClientThread ct = new ClientThread(s, this);
        Thread t = new Thread();
        t.start();
        clientList.add(ct);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
