package com.example.minimezun;

public class ProfileModel {
    String name;
    String surname;
    String yearOfGraduation;
    String companyName;

    String bachelors;

    String city;

    String email;

    String phoneNumber;

    String id;

    public ProfileModel(String name, String surname, String yearOfGraduation, String companyName, String bachelors, String city, String email, String phoneNumber, String id) {
        this.name = name;
        this.surname = surname;
        this.yearOfGraduation = yearOfGraduation;
        this.companyName = companyName;
        this.bachelors = bachelors;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBachelors() {
        return bachelors;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }



    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", yearOfGraduation='" + yearOfGraduation + '\'' +
                ", companyName='" + companyName + '\'' +
                ", bachelors='" + bachelors + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
