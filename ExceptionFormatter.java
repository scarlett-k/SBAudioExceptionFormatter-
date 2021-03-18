import java.io.*;
import java.util.*;

/**
 * @author Scarlett Kim
 * @version 1.0
 *
 * Uses BufferedReader to read through data.txt (file of exceptions with associated code block tags)
 * Adds tags and associated exceptions in map of string keys and map values
 * Prints output to output.txt file
 */
public class ExceptionFormatter {
    /**
     * @param args main method args
     * calls readData method
     */
    public static void main(String[] args) {
        readData();
    }

    /**
     * Uses BufferedReader to read data.txt file and store
     * tags and associated exceptions in a map
     *
     * Calls outputData method
     */
    public static void readData(){
        String line = "";
        String exceptionStr="";
        String id="";
        Map<String, Map<String, Integer>> map = new HashMap<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter filepath: ");
        String filepath = scan.next();
        System.out.println();
        boolean start=true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while((line=br.readLine())!= null) {
                if(line.equals("####################################")){
                    if(start) {
                        String test;
                        exceptionStr = br.readLine();
                        test = br.readLine().trim();
                        while(!test.isEmpty()) {
                            test= br.readLine();
                        }

                        id = br.readLine();
                        //if there is no tag
                        if(id.contains(" ")){
                            id = "       ";
                        }
                        if(map.containsKey(id)){ //if the id has already been added to map, check if list is null
                            Map <String, Integer> innerMap = map.get(id); //value of id (the innermap)
                            if(innerMap.containsKey(exceptionStr)){
                                innerMap.put(exceptionStr, innerMap.get(exceptionStr)+1);
                                map.put(id, innerMap);
                            }
                            else {
                                innerMap.put(exceptionStr, 1);
                                map.put(id, innerMap);
                            }
                        }
                        //if id hasn't been added to map, add exception to innermap, put key + innermap in outermap
                        else {
                            Map<String, Integer> mapOfExceptions = new HashMap<>();
                            mapOfExceptions.put(exceptionStr, 1);
                            map.put(id, mapOfExceptions);
                        }
                        start = false; //set start to false to show we are at the end of the block
                    }
                    else {
                        br.readLine();
                        start=true;
                    }
                }
            }
            outputData(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a new text file named output.txt and writes all console text to it
     * @param map map of tags and exceptions
     * @throws FileNotFoundException for printstream
     */
    public static void outputData(Map<String, Map<String, Integer>> map) throws FileNotFoundException {
        //outputs print to output.txt file
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        String header=String.format("%7s\t\t%20s\t\t%s%n", "COUNT", "TAG",  "EXCEPTION/DESC");
        System.out.print(header);
        for(int i =0; i < header.length(); i++){
            System.out.print("- ");
        }
        System.out.println();

        ArrayList<ExceptionLine> listOfExceptions = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
            for(Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                String tag    = innerEntry.getKey();
                Integer cnt  = innerEntry.getValue();
                ExceptionLine exceptionLine = new ExceptionLine(cnt, entry.getKey(), tag);
                listOfExceptions.add(exceptionLine);
            }
        }
        Collections.sort(listOfExceptions);
        for(Object str: listOfExceptions){
            System.out.println(str);
        }
    }
}



