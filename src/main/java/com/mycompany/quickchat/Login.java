/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author Ondwela Mathobo
 */
public class Login {
    // global variables that can be accessed throughout the program 

    String storedusername;
    String storedpassword;
    String storedcellphone;
    String firstname;
    String lastname;

    Scanner inputuser = new Scanner(System.in);

    // a return method to check username 
    boolean checkUserName(String username) {

        if (username.length() <= 5 && username.contains("_")) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }

    }
    // a return method to check password requirements

    boolean checkPasswordComplexity(String password) {

        // check each character in the password
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            }
            if (Character.isDigit(ch)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        if (password.length() >= 8 && hasUppercase && hasNumber && hasSpecialChar) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println(" Password is not correctly formatted ; please ensure that password contains at least eight characters, a capital letter,a number,and a special character.");
            return false;
        }
    }
    //method checking the validation of cellphone

    boolean checkCellPhoneNumber(String cellphone) {
        if (cellphone.startsWith("+27") && cellphone.length() == 12) {
            String numberPart = cellphone.substring(3);

            if (numberPart.matches("\\d{9}")) {
                System.out.println(" Cell phone number successfully added.");
                return true;

            }
        }
        System.out.println(" Cell phone number incorrectly formatted or does not contain international code.");
        return false;
    }

    // creating a void method called registerUser since we wont be returning a value
    void registerUser() {
        System.out.println("Enter FirstName");
        firstname = inputuser.nextLine();
        System.out.println("Enter LastName");
        lastname = inputuser.nextLine();
        System.out.println("\n =========REGISTER=========");
        System.out.println("Enter Username");
        //nextline is used for string
        String username = inputuser.nextLine();
        System.out.println("Enter password");
        String password = inputuser.nextLine();
        System.out.println(" Enter cellphone number");
        String cellphone = inputuser.nextLine();

        //if all conditions are true
        if (checkUserName(username) && checkPasswordComplexity(password) && checkCellPhoneNumber(cellphone)) {
            storedusername = username;
            storedpassword = password;
            storedcellphone = cellphone;
            System.out.println("Registered successfully");
        } else {
            System.out.println("Registration failed ,try again");
        }

    }
    // creating a boolean method called loginUser since we wont be returning a value  

    boolean loginUser() {
        if (storedusername == null) {
            System.out.println("No user registered yet");
            return false;

        }
        int attempts = 3;
        boolean success = false;

        System.out.println("\n=======LOGIN=======");

        while (attempts > 0 && !success) {

            System.out.println("Enter Username");
            String username = inputuser.nextLine();
            System.out.println("Enter password");
            String password = inputuser.nextLine();
            //equals () compares string values

            if (username.equals(storedusername) && password.equals(storedpassword)) {
                System.out.println("Welcome " + firstname + " " + lastname + ",it is great to see you again.");
                success = true;
            } else {

                attempts--;//decrease attempts
                if (attempts > 0) {
                    System.out.println("Username or password incorrect ,please try again. Attempts left:" + attempts);

                }
            }
        }
        if (!success) {
            System.out.println(" Too many failed attempts. Account is locked");
        }
        return success;
    }

    String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Login successful. Welcome!";
        } else {
            return "Login failed.Please check your info.";

        }

    }

}
