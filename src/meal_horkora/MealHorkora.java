package meal_horkora;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mehed
 */
public class MealHorkora {

    public int connection_buuton = 0;
    public int login_confirmation = 0;
    public int meal_rate = 0;
    public int serveDinner = 0;
    public int serveLunch = 0;
    public int initialBalance = 0;
    public int nextID;
    public int serial = 0;
    public int totalCustomer = 0;

    public int signUpLunch = 0;
    public int signUpDinner = 0;

    public String LunchOnOrOff = null;
    public String DinnerOnOrOff = null;

    public String customerPin = null;
    public String idSearch = null;
    public String customerAddress = null;

//    public String mainDirectory = "C:\\Users\\mehed\\Documents\\NetBeansProjects\\MealHorkora\\";
//    public String fileDirectory = "C:\\Users\\mehed\\Documents\\NetBeansProjects\\MealHorkora";
    
    public String mainDirectory = "C:\\Users\\mahdi\\Documents\\MealHorkora\\";
    public String fileDirectory = "C:\\Users\\mahdi\\Documents\\MealHorkora";

    MealHorkora() throws IOException {
        MealGUI ml = new MealGUI(this);
        ml.setVisible(true);
        System.out.println("Constructor MealHorkora");

//        loginPage mlp = new loginPage(this);
//        mlp.setTitle("Login Panel");
//        mlp.setVisible(true);
    }

    /////////////////////////// For New Customer //////////////////////////////////////////
    void customerIdName() throws IOException {

        String customerIDFile = mainDirectory + "customerIdName.csv";
        String line = "";
        String cvsSplitBy = ",";
        String[] temp = null;

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                temp = line.split(cvsSplitBy);

            }
            serial = Integer.parseInt(temp[0].replaceAll("\\D", ""));
            System.out.println(serial);
        }

    }

    void csvWrite(String sCustomerName, String sCustomerID, String sCustomerAddress, String sCustomerContact, String sRestrictFood) throws IOException {

        String fileName = "CMH" + String.valueOf(serial + 1) + "." + "csv";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));

        if (signUpLunch == 1 && signUpDinner == 0) {
            csvWriter.writeNext(new String[]{"Name", "RID", "Address", "Contact", "Restriction", "Date", "Lunch", "Lunch On/Off", "Dinner",
                "Dinner On/Off", "Guest Meal", "Meal Rate", "Bill", " ", "Balance", " ", "Payment", "", "", "PLS", "PDS"});
            csvWriter.writeNext(new String[]{sCustomerName, sCustomerID, sCustomerAddress, sCustomerContact, sRestrictFood});
            csvWriter.writeNext(new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"});
            csvWriter.writeNext(new String[]{"", "", "", "", "", "", "0", "ON", "0", "OFF", "0", "0", "0", "", String.valueOf(initialBalance), "", ""});

            csvWriter.close();
        }

        if (signUpLunch == 0 && signUpDinner == 1) {
            csvWriter.writeNext(new String[]{"Name", "RID", "Address", "Contact", "Restriction", "Date", "Lunch", "Lunch On/Off", "Dinner",
                "Dinner On/Off", "Guest Meal", "Meal Rate", "Bill", " ", "Balance", " ", "Payment", "", "", "PLS", "PDS"});
            csvWriter.writeNext(new String[]{sCustomerName, sCustomerID, sCustomerAddress, sCustomerContact, sRestrictFood});
            csvWriter.writeNext(new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"});
            csvWriter.writeNext(new String[]{"", "", "", "", "", "", "0", "OFF", "0", "ON", "0", "0", "0", "", String.valueOf(initialBalance), "", ""});

            csvWriter.close();
        }

        if (signUpLunch == 1 && signUpDinner == 1) {
            csvWriter.writeNext(new String[]{"Name", "RID", "Address", "Contact", "Restriction", "Date", "Lunch", "Lunch On/Off", "Dinner",
                "Dinner On/Off", "Guest Meal", "Meal Rate", "Bill", " ", "Balance", " ", "Payment", "", "", "PLS", "PDS"});
            csvWriter.writeNext(new String[]{sCustomerName, sCustomerID, sCustomerAddress, sCustomerContact, sRestrictFood});
            csvWriter.writeNext(new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"});
            csvWriter.writeNext(new String[]{"", "", "", "", "", "", "0", "ON", "0", "ON", "0", "0", "0", "", String.valueOf(initialBalance), "", ""});

            csvWriter.close();
        }

        ///////////////// Customer list e add korar jonno  ///////
        String csvCustomer = mainDirectory + "customerIdName.csv";
        CSVWriter customerWriter = new CSVWriter(new FileWriter(csvCustomer, true));
        customerWriter.writeNext(new String[]{"CMH" + String.valueOf(serial + 1), sCustomerName, customerPin});
        customerWriter.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    void pinCreator() {
        SecureRandom random = new SecureRandom();
        int tempPin = 0;

        while (true) {
            tempPin = random.nextInt(9999);
            if (tempPin > 1000) {
                customerPin = String.format("%04d", tempPin);
                break;
            }
        }

    }

    /////// serch file /////
    void lunchOnChecking(String ID) throws IOException {

        String filename = null;

        String temporaryID = ID + ".csv";
        collectAddress(temporaryID);

        File dir = new File(fileDirectory);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(temporaryID);
            }
        };
        String[] children = dir.list(filter);
        if (children == null) {

            JOptionPane.showMessageDialog(null, "Either dir does not exist or is not a directory");
        } else {

            for (int i = 0; i < children.length; i++) {
                filename = children[i];
                //System.out.println("File Found: " + filename);
            }

            if (children.length == 1) {
                lunchOnOffCheck(temporaryID);
                if (LunchOnOrOff.equalsIgnoreCase("ON")) {
                    System.out.println("dhukse");
                    String csvLunchOn = mainDirectory + "CustomerLunchList.csv";
                    CSVWriter lunchWriter = new CSVWriter(new FileWriter(csvLunchOn, true));
                    lunchWriter.writeNext(new String[]{ID, "", customerAddress});
                    lunchWriter.close();
                    System.out.println("lunch on!");
                }
            }

            if (children.length == 0) {
                // System.out.println("File Not Found!");
                JOptionPane.showMessageDialog(null, ID + " File Missing!");
            }
        }

    }

    void dinnerOnChecking(String ID) throws IOException {

        String filename = null;

        String temporaryID = ID + ".csv";
        collectAddress(temporaryID);

        File dir = new File(fileDirectory);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(temporaryID);
            }
        };
        String[] children = dir.list(filter);
        if (children == null) {
            JOptionPane.showMessageDialog(null, "Either dir does not exist or is not a directory");
        } else {

            for (int i = 0; i < children.length; i++) {
                filename = children[i];
                //System.out.println("File Found: " + filename);
            }

            if (children.length == 1) {
                dinnerOnOffCheck(temporaryID);
                if (DinnerOnOrOff.equalsIgnoreCase("ON")) {
                    /// customer meal on ar address neyar jonno
                    String csvDinnerOn = mainDirectory + "CustomerDinnerList.csv";
                    CSVWriter dinnerWriter = new CSVWriter(new FileWriter(csvDinnerOn, true));
                    dinnerWriter.writeNext(new String[]{ID, "", customerAddress});
                    dinnerWriter.close();
                }
            }

            if (children.length == 0) {
                //System.out.println("File Not Found!");
                JOptionPane.showMessageDialog(null, ID + " File Missing!");
            }
        }

    }

    void totalCustomerCounter() {
        File dir = new File(fileDirectory);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("CMH");
            }
        };
        String[] children = dir.list(filter);

        totalCustomer = children.length;
        JOptionPane.showMessageDialog(null, "Total Customer: " + totalCustomer);
    }

    void clearLunchList() throws IOException {

        String csvLunchOn = mainDirectory + "CustomerLunchList.csv";
        CSVWriter lunchWriter = new CSVWriter(new FileWriter(csvLunchOn));
        //lunchWriter.writeNext(new String[]{""});
        lunchWriter.close();
    }

    void clearDinnerList() throws IOException {

        String csvDinnerOn = mainDirectory + "CustomerDinnerList.csv";
        CSVWriter dinnerWriter = new CSVWriter(new FileWriter(csvDinnerOn));
        //dinnerWriter.writeNext(new String[]{""});
        dinnerWriter.close();
    }

    void lunchOnOffCheck(String newID) throws FileNotFoundException, IOException {
        String customerIDFile = mainDirectory + newID;
        String line = "";
        String cvsSplitBy = ",";
        String[] temp = null;

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                temp = line.split(cvsSplitBy);

            }
            LunchOnOrOff = temp[7].replace("\"", "");
            System.out.println(LunchOnOrOff);

        }
    }

    void dinnerOnOffCheck(String newID) throws FileNotFoundException, IOException {
        String customerIDFile = mainDirectory + newID;
        String line = "";
        String cvsSplitBy = ",";
        String[] temp = null;

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                temp = line.split(cvsSplitBy);

            }
            DinnerOnOrOff = temp[9].replace("\"", "");
            // System.out.println(temp[9]);

        }
    }

    void makeLunchList() throws IOException {
        totalCustomerCounter();
        clearLunchList();

        for (int i = 1; i <= totalCustomer; i++) {
            String tempID = "CMH" + i;
            lunchOnChecking(tempID);
        }
    }

    void makeDinnerList() throws IOException {
        totalCustomerCounter();
        clearDinnerList();
        for (int i = 1; i <= totalCustomer; i++) {
            String tempID = "CMH" + i;
            dinnerOnChecking(tempID);
        }

    }

    void lunchToCustomer() throws FileNotFoundException, IOException {
        String customerIDFile = mainDirectory + "CustomerLunchList.csv";
        String line = "";
        String cvsSplitBy = ",";
        String[] temp = null;
        csvHandle mealWriterL = new csvHandle();

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {
                System.out.println("lunchToCustomer");

                temp = line.split(cvsSplitBy);

                String lunchFile = temp[0].replace("\"", "") + ".csv";
                System.out.println(lunchFile);

                mealWriterL.csvWriteLunch(lunchFile);

            }

        }

    }

    void dinnerToCustomer() throws FileNotFoundException, IOException {
        String customerIDFile = mainDirectory + "CustomerDinnerList.csv";
        String line = "";
        String cvsSplitBy = ",";
        String[] temp = null;
        csvHandle mealWriterD = new csvHandle();

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                temp = line.split(cvsSplitBy);

                String dinnerFile = temp[0].replace("\"", "") + ".csv";
                System.out.println(dinnerFile);
                mealWriterD.csvWriteDinner(dinnerFile);

            }

        }

    }

    void collectAddress(String getID) throws FileNotFoundException, IOException {
        String customerIDFile = mainDirectory + getID;
        String line = "";
        String cvsSplitBy = ",";
        String[] temp;
        int lineCounter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(customerIDFile))) {

            while ((line = br.readLine()) != null) {

                if (lineCounter > 0 && lineCounter == 1) {
                    temp = line.split(cvsSplitBy);
                    customerAddress = temp[2].replace("\"", "");
                    //System.out.println(customerAddress.replace("\"", ""));
                    break;

                }

                //fileOpen(newFile);
                lineCounter++;

            }
            //LunchOff = Integer.parseInt(temp[7].replaceAll("\\D", ""));

        }
    }

    public static void main(String[] args) throws IOException {

        new MealHorkora();

    }
}
