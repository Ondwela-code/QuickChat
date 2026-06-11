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
            System.out.println("4. Stored Messages");
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
                        Message msg = new Message(r, m, sentMessages.size() + storedMessages.size()+disregardedMessages.size() +1);

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

                        
                    }
                    // Display total messages sent after all messages processed
                    System.out.println("Total messages sent: " + totalMessagesSent);
                    break;

                case 2:
                    displayAllSentMessages();
                    break;

                case 3:
                    System.out.println("Goodbye");
                    break;
                    
                case 4:
                    storedMessagesMenu(sc);
                    break;
                    
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
    
    // new menu method
    private static void storedMessagesMenu(Scanner sc){
        int subChoice;
        do{
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a. Display all stored messages");
            System.out.println("b. Display longest stored message");
            System.out.println("c. Search by Message ID");
            System.out.println("d. Search by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display report");
            System.out.println("0. Back to main menu");
            System.out.print("Choose option: ");
            String input = sc.nextLine().trim().toLowerCase();
            
            switch (input){
                case "a":
                    displayStoredMessages();
                    break;
                case "b":
                    System.out.println(displayLongestMessage());
                    break;
                    case "c":
                    System.out.print("Enter Message ID: ");
                    String searchID = sc.nextLine();
                    System.out.println(searchByMessageID(searchID));
                    break;
                case "d":
                    System.out.print("Enter recipient number: ");
                    String searchRecipient = sc.nextLine();
                    System.out.println(searchByRecipient(searchRecipient));
                    break;
                case "e":
                    System.out.print("Enter message hash to delete: ");
                    String hash = sc.nextLine();
                    System.out.println(deleteMessage(hash));
                    break;
                case "f":
                    System.out.println(displayReport());
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid option.");
                    input = "";
            }
            subChoice = input.equals("0") ? 0 : 1;
        } while (subChoice != 0);
            }
       //loops through sentMessages and prints each with hash 
    public static void displayAllSentMessages(){
    if (sentMessages.isEmpty()){
        System.out.println(" No sent messages yet.");
        return;
    }
            System.out.println("\n--- Sent Messages ---");
            for (Message msg:sentMessages){
                System.out.println(msg.printMessages());
                System.out.println("----------------------");
            }
    
}
    // loops through storedMessages and prints each
  public static void displayStoredMessages() {
      if (storedMessages.isEmpty()) {
          System.out.println("No stored messages yet.");
          return;
      }
       System.out.println("\n--- Stored Messages ---");
       for (Message msg : storedMessages){
           System.out.println(msg.printMessages());
           System.out.println("-----------------------");
       }
  }   
 //searches sent + stored , finds longest message text   
public static String displayLongestMessage(){
    ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            return "No messages found.";
        }

        Message longest = allMessages.get(0);
        for (Message msg : allMessages) {
            if (msg.message.length() > longest.message.length()) {
                longest = msg;
            }
        }
        return "Longest message: " + longest.message;
    }
//searches all messages by ID, RETURNS reciepient + message
public static String searchByMessageID(String id){
    ArrayList<Message>allMessages= new ArrayList<>();
    allMessages.addAll(sentMessages);
    allMessages.addAll(storedMessages);
    
    for (Message msg : allMessages ){
        if (msg.messageID.equals(id)){
            return "Recipient:" + msg.recipient + "\nMessage:" + msg.message;
        }
    }
    return "Message ID not found.";
}

//searches all messages by reciepient number
public static String searchByRecipient(String recipient){
    ArrayList<Message> allMessages = new ArrayList<>();
    allMessages.addAll(sentMessages);
    allMessages.addAll(storedMessages);
    
    StringBuilder results = new StringBuilder();
    for (Message msg : allMessages) {
        if (msg.recipient.equals(recipient)) {
            results.append(msg.printMessages()).append ("\n-----------------\n");
        }
    }
    if (results.length()==0){
        return "No messages found for recipient:" + recipient ;
    }
    return results.toString();  
}
//deletes message from sent or stored using hash
public static String deleteMessage(String hash) {
    // Search for sent messages
    for(int i = 0; i< sentMessages.size(); i++) {
        if (sentMessages.get(i).messageHash.equals(hash)){
            String deleteMsg = sentMessages.get(i).message;
            sentMessages.remove(i);
            messageHashArray.remove(hash);
            saveToJSON();
            return "Message: \" " + deleteMsg + "\" successfully deleted.";
            
        }
    }
   //search stored messages
   for (int i = 0; i < storedMessages.size(); i++){
       if (storedMessages.get(i).messageHash.equals(hash)){
           String deleteMsg = storedMessages.get(i).message;
            storedMessages.remove(i);
            messageHashArray.remove(hash);
            saveToJSON();
            return "Message: \" " + deleteMsg + "\" successfully deleted.";
               
       }
   }
    return "Message hash not found.";
}
 
    public static String displayReport(){
        if (sentMessages.isEmpty()){
            return "No sent messages to report.";
        }
        StringBuilder report = new StringBuilder();
        report.append("\n====== MESSAGE REPORT ======\n");
        for (Message msg : sentMessages) {
            report.append("Message Hash: ").append(msg.messageHash).append("\n");
            report.append("Recipient:    ").append(msg.recipient).append("\n");
            report.append("Message:      ").append(msg.message).append("\n");
            report.append("----------------------------\n");
        }
        return report.toString();
        }
    


    //Properties of each message
    private String messageID;
    private int numMessage;
    private String recipient;
    private String message;
    private String messageHash;
    private String flag;

    public Message(String recipient, String message, int numMessage) {
        this.recipient = recipient;
        this.message = message;
        this.numMessage = numMessage;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.flag = "Pending";
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
                this.flag= "Sent";
                sentMessages.add(this);
                totalMessagesSent++;
                messageHashArray.add(this.messageHash);
                messageIDArray.add(this.messageID);
                saveToJSON();
                return "Message successfully sent.";

            case "store":
                this.flag= "Stored";
                storedMessages.add(this);
               
                messageHashArray.add(this.messageHash);
                messageIDArray.add(this.messageID);
                saveToJSON();
                return "Message successfully stored.";

                
                
            case "discard":
                this.flag = "Disregarded";
                disregardedMessages.add(this);
                return "Press 0 to delete the message";

            default:
                return "Invalid choice. Message disregarded";
        }
    }

    // output of processed messages
    public String printMessages() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: "
                + recipient + "\nMessage: " + message +"\nFlag:" + flag;
    }

    // returns total number of messages sent
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // stores messages
    public void storeMessage() {
        this.flag = "Stored";
        
        storedMessages.add(this);
        messageHashArray.add(this.messageHash);
        messageIDArray.add(this.messageID);
        saveToJSON();
    }

    // checks if message length is no more than 250 characters
    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    public String getMessageID() {
        return messageID;
    }
    public String getMessageHash(){
        return messageHash;
    }
public String getMessage() {
    return message;
    
}

public String getRecipient(){
    return recipient;
}

public String getFlag(){
    return flag;
}
    // saving to json
    private static void saveToJSON() {
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            ArrayList<Message>allToSave = new ArrayList<>();
            allToSave.addAll(sentMessages);
            allToSave.addAll(storedMessages);
            gson.toJson(allToSave, writer);
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
               for (Message msg : loaded) {
                    if ("Sent".equals(msg.flag)) {
                        sentMessages.add(msg);
                        messageHashArray.add(msg.messageHash);
                        messageIDArray.add(msg.messageID);
                    } else if ("Stored".equals(msg.flag)) {
                        storedMessages.add(msg);
                        messageHashArray.add(msg.messageHash);
                        messageIDArray.add(msg.messageID);
                    }
                }
            }
        } catch (IOException e) {
            sentMessages = new ArrayList<>();
            storedMessages = new ArrayList<>();
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
