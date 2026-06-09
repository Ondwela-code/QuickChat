/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.quickchat;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 *
 * @author Ondwela Mathobo
 */
public class Message {
// JSON declarations
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();
    static ArrayList<Message>disregardedMessages= new ArrayList<>();
    static ArrayList<String>messageHashArray= new ArrayList<>();
    static ArrayList<String>messageIDArray= new ArrayList<>();
    
    private static int totalMessagesSent = 0;
    private static final String STORAGE_FILE = "storedMessages.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Main menu after successfully logging in
    public static void main(String[] args) {
        loadStoredMessages();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to QuickChat.");

        int choice;
        do {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("How many messages? ");
                    int count = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Recipient: ");
                        String r = sc.nextLine();
                        System.out.print("Message: ");
                        String m = sc.nextLine();
                        Message msg = new Message(r, m, sentMessages.size() + 1);

                        // Check recipient cell format first
                        String cellCheck = msg.checkRecipientCell();
                        if (!cellCheck.equals("Cell phone number successfully captured.")) {
                            System.out.println(cellCheck);
                            continue;
                        }

                        // Check message length
                        if (!msg.checkMessageLength()) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            continue;
                        }

                        // Ask user what to do with the message
                        System.out.println("\nMessage ready:");
                        System.out.println("To: " + r);
                        System.out.println("Message: " + m);
                        System.out.println("1. Send Message");
                        System.out.println("2. Store Message");
                        System.out.println("3. Disregard Message");
                        System.out.print("Choose option: ");
                        int msgChoice = sc.nextInt();
                        sc.nextLine();

                        String action;
                        switch (msgChoice) {
                            case 1: action = "send"; break;
                            case 2: action = "store"; break;
                            case 3: action = "discard"; break;
                            default: action = "discard"; break;
                        }

                        System.out.println(msg.SentMessage(action));

                        // Print full message details after sending
                        if (action.equals("send")) {
                            System.out.println(msg.printMessages());
                        }
                    }
                    // Display total messages sent after all messages processed
                    System.out.println("Total messages sent: " + totalMessagesSent);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    break;
            }
        } while (choice != 3);
    }

    //Properties of each message
    private String messageID;
    private int numMessage;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(String recipient, String message, int numMessage) {
        this.recipient = recipient;
        this.message = message;
        this.numMessage = numMessage;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // generates a random 10 digit number
    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // checks if message ID is not longer than 10 digits
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // checks if cellphone starts with +27 and is not more than 13 characters
    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (recipient.startsWith("+27") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // creates and returns the message hash
    public String createMessageHash() {
        String idStart = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        this.messageHash = idStart + ":" + numMessage + ":" + firstWord + lastWord;
        return this.messageHash;
    }

    // the sending messages process - allows user to send, store or disregard
    public String SentMessage(String action) {
        switch (action.toLowerCase()) {
            case "send":
                sentMessages.add(this);
                totalMessagesSent++;
                saveToJSON();
                return "Message successfully sent.";

            case "store":
                storeMessage();
                return "Message successfully stored.";

            case "discard":
                return "Press 0 to delete the message";

            default:
                return "Invalid choice. Message disregarded";
        }
    }

    // output of processed messages
    public String printMessages() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: "
                + recipient + "\nMessage: " + message;
    }

    // returns total number of messages sent
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // stores messages
    public void storeMessage() {
        sentMessages.add(this);
        saveToJSON();
    }

    // checks if message length is no more than 250 characters
    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    public String getMessageID() {
        return messageID;
    }

    // saving to json
    private void saveToJSON() {
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(sentMessages, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // retrieves sent messages so you can still see them
    private static void loadStoredMessages() {
        try (FileReader reader = new FileReader(STORAGE_FILE)) {
            Type listType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            ArrayList<Message> loaded = gson.fromJson(reader, listType);

            if (loaded != null) {
                sentMessages = loaded;
            }
        } catch (IOException e) {
            sentMessages = new ArrayList<>();
        }
    }

}

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.*;
//
///**
// *
// * @author Ondwela Mathobo
// */
//public class Message {
//// JSON declarations
//    static ArrayList<Message> sentMessages = new ArrayList<>();
//    private static ArrayList<Message> storedMessages = new ArrayList<>();
//    private static int totalMessagesSent = 0;
//    private static final String STORAGE_FILE = "storedMessages.json";
//    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    //Load from JSON when program starts
//
//    //static {
//      //  loadStoredMessages();
//
//   // }
//    
//    //Main menu after successfully logging in
//  public static void main(String[] args) {
//    loadStoredMessages();
//    Scanner sc = new Scanner(System.in);
//    System.out.println("Welcome to QuickChat");
//
//    int choice;
//    do {
//        System.out.println("\n1. Send Messages");
//        System.out.println("2. Show recently sent messages");
//        System.out.println("3. Quit");
//        System.out.print("Enter choice: ");
//        choice = sc.nextInt();
//        sc.nextLine();
//
//        switch (choice) {
//            case 1:
//                System.out.print("How many messages? ");
//                int count = sc.nextInt();
//                sc.nextLine();
//                for (int i = 0; i < count; i++) {
//                    System.out.print("Recipient: ");
//                    String r = sc.nextLine();
//                    System.out.print("Message: ");
//                    String m = sc.nextLine();
//                  Message msg = new Message(r, m, sentMessages.size() + 1);
//
//if (msg.checkMessageLength()) {
//    msg.storeMessage();
//    System.out.println("Message sent!");
//} else {
//    System.out.println("Please enter a message of less than 250 characters.");
//}
//                }
//                break;
//                
//            case 2:
//                System.out.println("Coming soon");
//                break;
//                
//            case 3:
//                break;
//        }
//    } while (choice != 3);
//} 
//  //Properties of each message
//    private String messageID;
//    private int numMessage;
//    private String recipient;
//    private String message;
//    private String messageHash;
//
//    public Message(String recipient, String message, int numMessage) {
//
//        this.recipient = recipient;
//        this.message = message;
//        this.numMessage = numMessage;
//        this.messageID = generateMessageID();
//        
//        this.messageHash = createMessageHash();
//
//    }
//    
////generates a random 10 digit number
//    private String generateMessageID() {
//        Random rand = new Random();
//        return String.format("%010d", rand.nextInt(1000000));
//    }
////checks if message ID is not longer than 10 digits
//    public boolean checkMessageID() {
//        return messageID != null && messageID.length() <= 10;
//
//    }
////checks if cellphone starts with +27  and is not more than 10 characters
//    public String checkRecipientCell() {
//        if (recipient == null) {
//            return "Cell number is incorrectly formatted";
//        }
//        if (recipient.length() <= 10 && recipient.startsWith("+27")) {
//            return "Cell phone number successfully captured";
//        }
//        return "Cell number incorrectly formatted";
//    }
////doesnt work but its purpose is to hash messages
//    public String createMessageHash() {
//        String idStart = messageID.substring(0, 2);
//        String[] words = message.trim().split("\\s+");
//        String firstWord = words[0].toUpperCase();
//        String lastWord = words[words.length - 1].toUpperCase();
//        this.messageHash = idStart + ":" + numMessage + ":" + firstWord + lastWord;
//        return this.messageHash;
//    }
////the sending messages process
//    public String SentMessage(java.util.Scanner sc) {
//        System.out.println("\nMessage ready:");
//        System.out.println("To:" + recipient);
//        System.out.println("Message:" + message);
//        System.out.println("1.Send Message");
//        System.out.println("2. Store Message");
//        System.out.println("3.Disregard Message");
//        System.out.println("Choose option:");
//
//        int choice = sc.nextInt();
//        sc.nextLine();
//
//        switch (choice) {
//            case 1:
//                sentMessages.add(this);
//                saveToJSON();
//                return "Message successfully sent";
//
//            case 2:
//                storeMessage();
//                return "Message successfully stored.";
//
//            case 3:
//                return "Press 0 to delete the message";
//            default:
//                return "Invalid choice. Message disregarded";
//
//        }
//    }
////output of processed messages
//    public String printMessages() {
//        return "Message ID:" + messageID + "\nMessage Hash:" + messageHash + "\nRecipient:"
//                + recipient + "\nMessage:" + message;
//
//    }
//
//    public int returnTotalMessagess() {
//        return sentMessages.size();
//    }
////stores messages
//    public void storeMessage() {
//        sentMessages.add(this);
//        saveToJSON();
//    }
////checks if message length is no more than 250 characters
//    public boolean checkMessageLength() {
//        return message.length() <= 250;
//    }
//
//    public String getMessageID() {
//        return messageID;
//    }
////saving to json
//    private void saveToJSON() {
//        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
//            gson.toJson(sentMessages, writer);
//        } catch (IOException e) {
//            System.out.println("Error saving to JSON:" + e.getMessage());
//        }
//    }
////retrieves sent messages so you can still see
//    private static void loadStoredMessages() {
//        try (FileReader reader = new FileReader(STORAGE_FILE)) {
//            Type listType = new TypeToken<ArrayList<Message>>() {
//            }.getType();
//            ArrayList<Message> loaded = gson.fromJson(reader, listType);
//
//            if (loaded != null) {
//                sentMessages = loaded;
//            }
//        } catch (IOException e) {
//            sentMessages = new ArrayList<>();
//        }
//    }
//
//}
