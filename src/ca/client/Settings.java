/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.client;

import java.util.ArrayList;
import ca.utils.FileHandler;

/**
 *
 * @author Marc
 */
public class Settings {
    
    Client origin;
    private String name;
    FileHandler fh;
    String path = "settings.txt";
    ArrayList<ServerInfo> servers;

    public Settings(Client origin) {
        this.origin = origin;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ServerInfo> getServers() {
        return servers;
    }

    public void setServers(ArrayList<ServerInfo> servers) {
        this.servers = servers;
    }

    public Settings() {
        fh = new FileHandler();
        servers = new ArrayList<ServerInfo>();
    } 
    
    public void loadSettings() {
        System.out.println("LOADING");
        ArrayList<String> loaded = fh.load(path);
        servers = new ArrayList<ServerInfo>();
        name = loaded.get(0);
        origin.setUserName(name);
        loaded.remove(0);
        for (String s : loaded) {
            String[] splitted = s.split(",");
            servers.add(new ServerInfo(splitted[0], splitted[1]));
        }
        origin.setServers(servers);
    }

    public void saveSettings() {
        ArrayList<String> saves = new ArrayList();
        saves.add(getName());
        if (!servers.isEmpty()) {
            for (ServerInfo s : servers) {
                saves.add(s.name + "," + s.ip);
            }
        }
        System.out.println("SAVING");
        fh.save(saves,path);
    }
    
}
