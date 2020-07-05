package meal_horkora;

import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class csvHandle  {
    
    public String[] lastLine = null;
    
    MealHorkora nmh;
    
   csvHandle() throws IOException
   {
       MealHorkora mhm = new MealHorkora();
       nmh=mhm;
       System.out.println("Constructor csv handle");
   }
    
    

    void csvWriteLunch(String rFile) throws IOException {

        String ID = rFile;
        System.out.println("Function called!");
        readLastState(ID);
        System.out.println("reading file no: " + ID);
        String csv = nmh.mainDirectory + ID;
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));

        Date d = new Date();
        String date = d.toString();

        writer.writeNext(new String[]{" ", " ", " ", " ", "", date, "1", lastLine[7].replace("\"", ""), "0", lastLine[9].replace("\"", ""), "0",String.valueOf(nmh.meal_rate)});

        writer.close();

    }

    void csvWriteDinner(String sFile) throws IOException {

        String ID = sFile;
        readLastState(ID);
        System.out.println("reading file no: " + ID);
        String csv = nmh.mainDirectory + ID;
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));

        Date d = new Date();
        String date = d.toString();

        writer.writeNext(new String[]{" ", " ", " ", " ", "", date, "0", lastLine[7].replace("\"", ""), "1", lastLine[9].replace("\"", ""), "0",String.valueOf(nmh.meal_rate)});

        writer.close();
    }

    ////////////////////////////////
    void readLastState(String cID) throws FileNotFoundException, IOException {
        String customerIDFile = nmh.mainDirectory + cID;
        String line = "";
        String cvsSplitBy = ",";
        

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                lastLine = line.split(cvsSplitBy);

            }
            

        }
    }
}
