package com.validity.TakeHomeChallenge.service;

import com.validity.TakeHomeChallenge.model.Customer;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

public class DuplicateFinderService {

    private static Map<Customer, List<Customer>> duplicateMap = new HashMap<>();
    private static List<Set<Customer>> duplicateList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private static Set<Customer> duplicate = new HashSet<>();

    private static DuplicateFinderService duplicateFinderService = null;
    private DuplicateFinderService(){

    }

    //singleton
    public static DuplicateFinderService getInstance(){
        if (duplicateFinderService == null){
            duplicateFinderService = new DuplicateFinderService();
        }
        return duplicateFinderService;
    }


   //main method to find duplicates
   //returns a map of customer with corresponding duplicates
    public List<Set<Customer>> findDuplicates(List<Customer> cList){

        if (!duplicateList.isEmpty()) {
            return duplicateList;
        }

        this.customerList = cList;
        //for each customer in customer list, check all
        // that proceed it in the list,
        // however, don't check records that are already found as duplicates
        for (int i=0; i<customerList.size()-1; i++){

            Customer currCustomer = customerList.get(i);
            //set for storing duplicate records for this particular customer
            Set<Customer> customerSet = new HashSet<>();
            //compares currCustomer to all following customers
            for (int j=i+1; j<customerList.size(); j++){

                Customer secondCustomer = customerList.get(j);

                //if secondCustomer is not found to be duplicate already, proceed
                if (!duplicate.contains(secondCustomer)){
                    //compare and check if they are duplicates
                    boolean isDuplicate = compareCustomers
                            (currCustomer,customerList.get(j));
                    //if yes, add them to set
                    if (isDuplicate){
                        customerSet.add(currCustomer);
                        customerSet.add(secondCustomer);
                        duplicate.add(currCustomer);
                        duplicate.add(secondCustomer);

                    }
                }

            }
            //add duplicate set of customer to list
            duplicateList.add(customerSet);
        }

        return duplicateList;

    }

    public List<Customer> getUnique(List<Customer> cList){

        //if duplicates are not found already, find them
        if (duplicateList.isEmpty()){
            findDuplicates(cList);
        }

        //remove all duplicate records from original list of records and return
        List<Customer> uniqueList = new ArrayList<>(customerList);
        uniqueList.removeAll(duplicate);
        return uniqueList;
    }

    //compares two customers given as input
    // and returns true iff they are likely to be duplicates
    private boolean compareCustomers(Customer cusOne, Customer cusTwo){

        Metaphone metaphone = new Metaphone();
        metaphone.setMaxCodeLen(12);

        String fNameOne = cusOne.getFirstName();
        String fNameTwo = cusTwo.getFirstName();
        String lNameOne = cusOne.getLastName();
        String lNameTwo = cusTwo.getLastName();

        //check if either the first name or last name sounds similar using Metaphone
        // we are checking both fields because if there was a record "Robert Doe"
        // and "Bob Doe", they could be the same person.
        boolean soundsSame =
                metaphone.isMetaphoneEqual(fNameOne, fNameTwo)
                || metaphone.isMetaphoneEqual(lNameOne, lNameTwo);


        if (soundsSame) {
            //get similarity score
            double finalScore = getSimilarityScore(cusOne, cusTwo, fNameOne,
                                                fNameTwo, lNameOne, lNameTwo);
            double THRESHHOLD = 0.8;
            //if similarity is more than threshold return true
            return finalScore >= THRESHHOLD;
        }
        return false;
    }

    //returns the similarity score between two customers
    private double getSimilarityScore(Customer cusOne, Customer cusTwo,
                                     String fNameOne, String fNameTwo,
                                     String lNameOne, String lNameTwo){


        double fNameScore, lNameScore, emailScore, phoneScore,
                addressScore, companyScore;


        //customer one details
        String emailOne = cusOne.getEmail();
        String phoneOne = cusOne.getPhone();
        String addressOne = cusOne.getAddress();
        String companyOne = cusOne.getCompany();

        //customer two details
        String emailTwo = cusTwo.getEmail();
        String phoneTwo = cusTwo.getPhone();
        String addressTwo = cusTwo.getAddress();
        String companyTwo = cusTwo.getCompany();

        //weights for each field
        double firstNameWeight = 0.15;
        double lastNameWeight = 0.15;
        double emailWeight = 0.35;
        double phoneWeight = 0.275;
        double addressWeight = 0.05;
        double companyWeight = 0.025;

        //calculates similarity score for each field and multiply by weight
        fNameScore = calculateSimilarity(fNameOne, fNameTwo) * firstNameWeight;
        lNameScore = calculateSimilarity(lNameOne, lNameTwo) * lastNameWeight;
        emailScore = calculateSimilarity(emailOne, emailTwo) * emailWeight;
        phoneScore = calculateSimilarity(phoneOne, phoneTwo) * phoneWeight;
        addressScore = calculateSimilarity(addressOne, addressTwo) * addressWeight;
        companyScore = calculateSimilarity(companyOne, companyTwo) * companyWeight;

        //add all scores and return
        return fNameScore + lNameScore + emailScore
                + phoneScore + addressScore + companyScore;
    }

    //calculates similarity score between two strings using levenshtein distance
    private double calculateSimilarity(String one, String two){
        LevenshteinDistance lD = new LevenshteinDistance();

        double longer = one.length();
        double shorter = two.length();

        //if any length is 0 (inconsistent data), return 1 to discard its effect
        if (longer == 0 || shorter == 0) return 1.0;

        if (longer<shorter){
            double temp = shorter;
            shorter = longer;
            longer = temp;
        }

        //return a probability score such that it is between 0 and 1
        return (longer - lD.apply(one, two))/longer;
    }

}

//Reference: https://stackoverflow.com/questions/955110/similarity-string-comparison-in-java