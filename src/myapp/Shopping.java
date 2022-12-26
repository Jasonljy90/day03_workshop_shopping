package myapp;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Shopping {
    public static void main(String[] args) throws Exception {
        ArrayList<String> shoppingList = new ArrayList<>();
        String user = "";
        Console cons = System.console();
        String input = "";
        while(!input.toLowerCase().equals("stop")){
            System.out.println("Welcome to your shopping cart");

            input = cons.readLine("> ");
            String inputSplit[] = input.split(" ");
                        
            switch (inputSplit[0]) {
                case "add":
                    add(shoppingList, input);
                    break;
                case "delete":
                    delete(shoppingList, inputSplit);
                    break;
                case "list":
                    list(shoppingList);
                    break;
                case "stop":
                    System.out.println("Good bye, have a nice day!");
                    break;
                case "login":
                    user = inputSplit[1];
                    Path path = Paths.get("src/cartdb/" + inputSplit[1] + ".db");

                    if (!Files.exists(path)) {
                        File myObj = new File("src/cartdb/" + inputSplit[1] + ".db");
                        myObj.createNewFile();
                    }else {
                        read(inputSplit[1], shoppingList);
                    }
                    break;
                case "save":
                    write(user, shoppingList);
                    break;
                case "users":
                    users();
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        }
        //System.out.println("Good bye, have a nice day!");
}
    
    // add
    private static void add(ArrayList<String> shoppingList, String input) {
        input = input.replaceFirst("add ", "");
        String[] individualFruit = input.split(", ");
        for(int i = 0; i < individualFruit.length; i++) {
            shoppingList.add(individualFruit[i]);
            System.out.println(individualFruit[i] + " added to cart");
        }
    }

    // list
    private static void list(ArrayList<String> shoppingList) {

        for(int i = 0; i < shoppingList.size(); i++) {
            System.out.println((i + 1) + ". " + shoppingList.get(i));
        }
    }

    // delete
    private static void delete(ArrayList<String> shoppingList, String[] inputSplit) {
        Integer index = Integer.parseInt(inputSplit[1]) - 1;
        if(index >= shoppingList.size() || index < 0) {
            System.out.println("Invalid item index");
        }else {
            System.out.println(shoppingList.get(index) + " removed from cart");
            shoppingList.remove(shoppingList.get(index));
        }
    }

    // read files and load to shoppingList array list
    private static void read(String name,  ArrayList<String> shoppingList) throws IOException {
        Path p = Paths.get("src/cartdb/" + name + ".db");
        InputStream is = new FileInputStream(p.toFile());
        InputStreamReader isr = new InputStreamReader(is);
        LineNumberReader lnr = new LineNumberReader(isr);
        String line = lnr.readLine();
        String[] lineSplit = line.split(", ");

        for (String fruit : lineSplit) {
            shoppingList.add(fruit);
        }

        // Closes all the stream
        is.close();
    }

    // write files
    private static void write(String name,  ArrayList<String> shoppingList) throws IOException {
        File myObj = new File("src/cartdb/" + name + ".db");
        
        FileWriter myWriter = new FileWriter(myObj);
        
        for (String fruit : shoppingList) {
            myWriter.write(fruit + ", ");
        }
            myWriter.close();
    }

    // list users
    private static void users() throws IOException {     
        File folder = new File("src/cartdb");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String input = listOfFiles[i].getName();
            input = input.replaceFirst(".db", "");
            System.out.println((i + 1) + "." + input);        
        }
    }
}
