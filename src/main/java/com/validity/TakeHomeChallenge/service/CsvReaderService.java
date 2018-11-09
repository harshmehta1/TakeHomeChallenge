package com.validity.TakeHomeChallenge.service;
import com.validity.TakeHomeChallenge.model.Customer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads provided CSV file and makes a list of Customers from the records
 */
public class CsvReaderService {

    //the file to read - normal or advanced
    private static final String fileToRead = "normal.csv";
    private static List<Customer> customerList = new ArrayList<>();
    private BufferedReader bufferedReader;

    private static CsvReaderService csvReaderService = null;

    //constructor runs only once
    // and thus reading the csv file is limited to one time
    private CsvReaderService(){
        this.readCustomerFromCSV();
    }

    //singleton
    public static CsvReaderService getInstance(){
        if (csvReaderService == null){
            csvReaderService = new CsvReaderService();
        }
        return csvReaderService;
    }


    //returns already read customer list
    public List<Customer> getCustomerList(){
        return customerList;
    }

    //reads records from CSV, makes a Customer obj with the values
    // and returns a list of these obj
    private List<Customer> readCustomerFromCSV(){

        if (!customerList.isEmpty()) {
            return customerList;
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(fileToRead));
            String line;
            //regex to split lines
            String csvSplit = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

            bufferedReader.readLine(); //ignoring titles
            while ((line = bufferedReader.readLine())!=null){
                String[] cDetails = line.split(csvSplit,-1);

                //setting fields of Customer object
                Customer customer = new Customer();
                customer.setId(cDetails[0]);
                customer.setFirstName(cDetails[1].toLowerCase());
                customer.setLastName(cDetails[2].toLowerCase());
                customer.setCompany(cDetails[3].toLowerCase());
                customer.setEmail(cDetails[4].toLowerCase());
                customer.setAddress(cDetails[5].toLowerCase()+" "+cDetails[6].toLowerCase());
                customer.setZip(cDetails[7].toLowerCase());
                customer.setCity(cDetails[8].toLowerCase());
                customer.setStateLong(cDetails[9].toLowerCase());
                customer.setState(cDetails[10].toLowerCase());
                customer.setPhone(cDetails[11]);

                //adding customer to customer list
                customerList.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return customerList;

    }

}
