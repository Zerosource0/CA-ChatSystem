/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ca.client.Client;
import ca.server.Server;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class TestClass {
    static Client c1 = new Client();
    static Client c2 = new Client();
    public TestClass() {
    }
    @BeforeClass
    public static void setUpClasses() throws IOException, InterruptedException
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Server.main(null);
            }
        }).start();
        
        Thread.sleep(2000); // It gives time for server to boot itself
        c1.setUserName("test1");
        c2.setUserName("test2");
        c1.connect("localhost", 9090);
        Thread.sleep(2000);
        c2.connect("localhost", 9090);
        Thread t1 = new Thread(c1);
        Thread t2= new Thread(c2);
        t1.start();
        t2.start();
        
    }
    
    @After
    public void stop() throws InterruptedException
    {
        
        Thread.sleep(1000);
        Server.stopServer();
        
    }
    

    @Test
    public void send() throws IOException, InterruptedException 
    {
       
       //Assert that the server has sent user lists, which after processed should return and empty string.
       assertEquals(c1.getMsg(),"");
       Thread.sleep(2000);
       //assertEquals(c1.getMsg(), "Server: Welcome the the Frenchy Chat test1!");
        c1.send("MSG#test2#hello");
        Thread.sleep(2000);
        assertEquals("test1: hello", c2.getMsg());
        c2.send("MSG#*#to all");
        Thread.sleep(2000);
        //assertEquals("","");
        
        assertEquals("test2: to all", c1.getMsg());
        Thread.sleep(2000);
        assertEquals("test2: to all", c2.getMsg());
       //assertEquals()
        
        
    }
}
    

