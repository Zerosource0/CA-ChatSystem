/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.utils;

/**
 *
 * @author marcj_000
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

    /**
     * This class handles writing to and from file.
     *
     * The methods are static so they can be called without making an object of
     * the class. If the application is designed with frequent file writing and
     * reading the a non-static method should be used instead.
     *
     *  EXAMPLE OF SAVING TO FILE:       
     *  ArrayList<String> as = new ArrayList<String>();
        as.add("Peter,3,8,12,13");
        as.add("Linda,18,2,4,5");
        as.add("Bob,8,12,4,6");
         
        FileHandlerStat.savePersons(as, "persons.txt");  
        
        EXAMPLE OF LOADING FROM FILE:
        ArrayList<String> outof = new ArrayList<String>();
        outof = FileHandlerStat.load("persons.txt");
        for (String line : outof) {
            System.out.println("Person: " + line);
        }   
     * 
     * @author Peter Lorensen
     */

public class FileHandler
{
    /**
     * The method loads strings from disk. The file resides in the project folder.
     *
     * Make sure to check for null before using the return value!
     *
     * @param filename String the name of the file (that is located in the project folder).
     * @return and ArrayList<String> were each String object is one line in the file.
     * If something goes wrong and an exception is raised this method will return null!
     */
    
    
    public static ArrayList<String> load(String filename)
    {
        Scanner file_scanner = null;
        ArrayList<String> stringArray = new ArrayList<String>();

        try {
            file_scanner = new Scanner(new File(filename));  //Connection to the file using the Scanner object
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find the file to load from! Returning null.");
            //ex.printStackTrace();
            return null;  //If something goes wrong the method returns null
        }

        while ( file_scanner.hasNextLine() ) {  //File found. Reading one line.             
            stringArray.add( file_scanner.nextLine() );  //Reading in a single line and saving in the ArrayList
        }

        file_scanner.close();  //Closing the file
        return stringArray;    //returning the arraylist
    }

    /**
     * This method saves an ArrayList of strings to disk.
     * Each string object in this array will be one line in the text file. 
     * The text file is overwritten, what ever was in there is lost. 
     *
     * @param stringArray ArrayList<String> Each String object in this array will be 
     * saved as one line in the text file. 
     * @params filename String the name of the file (that is located in the project folder).
     * @return true if everything went well. False if an exception was raised.
     */
    public static boolean save(ArrayList<String> stringArray, String filename)
    {
        if( stringArray == null ) { return false;  }  //Checking parameter for null.
        FileWriter output;  //Creating reference for filewriter.
        
        try {
                output = new FileWriter(new File(filename));  //Opening connection to file.
                for (String personline : stringArray) {   //running through the ArrayList.                    
                    output.write(personline.toString() + "\n");  //Each String object is written as a line in file.
            }

            output.close();  //Closing the file
        } catch (Exception ex) {  //If something goes wrong everything is send to system out.
            System.out.println("Could not save to file!");
            System.out.println(ex.toString());
            ex.printStackTrace();
            return false;  //If something goes wrong false is returned!
        }

        return true;
    }

}//END CLASS
