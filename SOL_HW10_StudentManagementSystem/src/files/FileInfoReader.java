package files;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to read the file.
 */
public class FileInfoReader {

    /**
     * Reads and parses given file.
     * Parses each line in file as an array and adds to list.
     * @param fileName to read
     * @return list of arrays
     */
    public static ArrayList<String[]> readInfo(String fileName){
        ArrayList<String[]> database = new ArrayList<>();
        File inputFile = new File(fileName);
        Scanner scanner;
        try{
            scanner = new Scanner(inputFile);
            String info;
            while(scanner.hasNextLine()){
                info = scanner.nextLine().trim();
                String[] infoArr = info.split(";");
                for(int i = 0; i <infoArr.length; i++){
                    infoArr[i] = infoArr[i].trim();
                }
                database.add(infoArr);

            }
            scanner.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return database;
    }

}
