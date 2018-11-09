package com.validity.TakeHomeChallenge.model;

public class Customer implements Comparable<Customer>{

    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String email;
    private String zip;
    private String city;
    private String stateLong;
    private String state;
    private String phone;

    public Customer(){

    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateLong() {
        return stateLong;
    }

    public void setStateLong(String stateLong) {
        this.stateLong = stateLong;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int compareTo(Customer c) {
        return this.firstName.compareTo(c.getFirstName());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(firstName).append(" ");
        s.append(lastName).append(" ");
        s.append(company).append(" ");
        s.append(email).append(" ");
        s.append(address).append(" ");
        s.append(zip).append(" ");
        s.append(city).append(" ");
        s.append(stateLong).append(" ");
        s.append(state).append(" ");
        s.append(phone);
        return s.toString();
    }
}
