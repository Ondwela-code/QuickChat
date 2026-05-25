/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author Ondwela Mathobo
 */
public class QuickChat {

    public static void main(String[] args) {

        Scanner enterMenu = new Scanner(System.in);
        //linking classes
        Login chatApp = new Login();
        //allows the user's choice
        int choice = 0;
        //do-while loop- runs the code before checking the condition
        // creating menu

        //do while loop to show the menu until user presses exit
        do {
            System.out.println("\n=====MENU====");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");

            System.out.println("Enter Menu option");
            choice = enterMenu.nextInt();
            switch (choice) {
                case 1:
                    chatApp.registerUser();
                    break;
                case 2:
                    chatApp.loginUser();
                    break;
                case 3:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 3);

        enterMenu.close();
    }
}
