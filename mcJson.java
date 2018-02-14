// Remington Howell
// Febuary 13, 2018
// JSON text generator for Minecraft Modding

// package minecraftmodjsongenerator;

import java.awt.GraphicsEnvironment;
import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class mcJson {
    
    static Scanner ifp;
    static String modId;
    static String directory;
    static String subDirBlockstates;
    static String subDirBlock;
    static String subDirItems;
    static boolean breakFlag = false;
    static boolean isABlock;

    public static void main(String[] args) throws IOException {
        ifp = new Scanner(System.in);
        String name;
        String data;
        byte[] stringToByte;
        boolean block;
        
        directory = System.getProperty("user.dir")+"\\minecraftJSON";
        Path xPath = Paths.get(directory);
        makeSubdomains(xPath);
        if(breakFlag){
            System.out.println("\npress any key to quit");
            return;
        }
        
        introduction();
        
        System.out.print("\t");
        name = ifp.next();
        
        
        while(!name.equals("quit")){
            String filename = name+".json";
            block = isABlock();
            
            if(block){
                // take care of item first
                xPath = Paths.get(subDirItems+"\\"+filename);
                data = makeJsonItems(name, true);
                stringToByte = data.getBytes();
                Files.write(xPath, stringToByte);
                
                // take care of blockstate
                xPath = Paths.get(subDirBlockstates+"\\"+filename);
                data = makeJsonBlockstates(name);
                stringToByte = data.getBytes();
                Files.write(xPath, stringToByte);
                
                // take care of block
                xPath = Paths.get(subDirBlock+"\\"+filename);
                data = makeJsonBlock(name);
                stringToByte = data.getBytes();
                Files.write(xPath, stringToByte);
            }
            else{
                xPath = Paths.get(subDirItems+"\\"+filename);
                data = makeJsonItems(name, false);
                stringToByte = data.getBytes();
                Files.write(xPath, stringToByte);
            }
            
            System.out.println("\nJSON files successfully created. Enter next block/item name, or enter \"quit\"");
            System.out.print("\t");
            name = ifp.next();
        }
        
        conclude();
        
    }

    private static void introduction() {
        System.out.println("This is a JSON generator which creates basic JSON files for minecraft blocks and items.");
        System.out.println("It will not (for now) make specific changes to the JSON other than block names, but if advisement for this is given this can be implemented");
        System.out.println("First, please provide your modId as it appears in your filesystem.");
        System.out.print("\t");
        modId = ifp.next();
        
        System.out.println("Enter the name of the json to be made (use "
                + "underscores instead of spaces), followed by a space and then "
                + "whether this object is a \"block\" or an \"item\".");
        System.out.println("DO NOT INCLUDE THE .JSON EXTENSION!!!");
        System.out.println("Enter \"quit\" (no quotes) to close this program.");
    }

    private static void conclude() {
        System.out.println("Your files are saved in "+directory);
        System.out.println("You may now go move the appropriate files into the appropriate folders");
    }
    
    private static void makeSubdomains(Path xPath) {
        String subDir;
        
        // making the main 
        if(!Files.exists(xPath)){
            System.out.println("Creating subdirectories...\n");
            try{
                Files.createDirectory(xPath); 
            }
            catch (Exception e){
                System.out.println("Could not create required directories");
                breakFlag = true;
            }
        }
        
        subDirBlockstates = directory+"\\blockstates";
        Path xPath2 = Paths.get(subDirBlockstates);
        
        if(!Files.exists(xPath2)){
            try{
                Files.createDirectory(xPath2); 
            }
            catch (Exception e){
                System.out.println("Could not create required directories");
                breakFlag = true;
            }
        }
        
        subDirBlock = directory+"\\block";
        xPath2 = Paths.get(subDirBlock);
        
        if(!Files.exists(xPath2)){
            try{
                Files.createDirectory(xPath2); 
            }
            catch (Exception e){
                System.out.println("Could not create required directories");
                breakFlag = true;
            }
        }
        
        subDirItems = directory+"\\items";
        xPath2 = Paths.get(subDirItems);
        
        if(!Files.exists(xPath2)){
            try{
                Files.createDirectory(xPath2); 
            }
            catch (Exception e){
                System.out.println("Could not create required directories");
                breakFlag = true;
            }
        }
    }

    private static String makeJsonItems(String name, boolean block) {
        String data = "";
        
        if(block){
            data += "{\n";
            data +=     "\t\"parent\": \""+modId+":block/"+name+"\"\n";
            data += "}";
        }
        else{
            data += "{\n";
            data +=     "\t\"parent\": \"item/generated\",\n";
            data +=     "\t\"textures\": {\n";
            data +=         "\t\t\"layer0\": \""+modId+":items/"+name+"\"\n";
            data +=     "\t}\n";
            data += "}";
        }
        
        return data;
    }

    private static String makeJsonBlockstates(String name) {
        String data = "";
        
        data += "{\n";
        data +=     "\t\"variants\": {\n";
        data +=         "\t\t\"normal\": { \"model\": \""+modId+":"+name+"\" }\n";
        data +=     "\t}\n";
        data += "}";
        
        return data;
    }

    private static String makeJsonBlock(String name) {
        String data = "";
        
        data += "{\n";
        data +=     "\t\"parent\": \"block/cube_all\",\n";
        data +=     "\t\"textures\": {\n";
        data +=         "\t\t\"all\": \""+modId+":blocks/"+name+"\"\n";
        data +=     "\t}\n";
        data += "}";
        
        return data;
    }

    private static boolean isABlock() {
        String s = ifp.next();
        while(true){
            if(s.equals("block")) 
                return true;
            else if(s.equals("item"))
                return false;
            else
                System.out.println("Invalid second input. Please enter 0 for block or 1 for item");
            System.out.print("\t");
            s = ifp.next();
        }
    }
    
}
