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

    static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private static final String STORAGE_FILE = "storedMessages.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //Load from JSON when program starts

    //static {
      //  loadStoredMessages();

   // }
  public static void main(String[] args) {
    loadStoredMessages();
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to QuickChat");

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

if (msg.checkMessageLength()) {
    msg.storeMessage();
    System.out.println("Message sent!");
} else {
    System.out.println("Please enter a message of less than 250 characters.");
}
                }
                break;
                
            case 2:
                System.out.println("Coming soon");
                break;
                
            case 3:
                break;
        }
    } while (choice != 3);
}  
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
    

    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000));
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;

    }

    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell number is incorrectly formatted";
        }
        if (recipient.length() <= 10 && recipient.startsWith("+27")) {
            return "Cell phone number successfully captured";
        }
        return "Cell number incorrectly formatted";
    }

    public String createMessageHash() {
        String idStart = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        this.messageHash = idStart + ":" + numMessage + ":" + firstWord + lastWord;
        return this.messageHash;
    }

    public String SentMessage(java.util.Scanner sc) {
        System.out.println("\nMessage ready:");
        System.out.println("To:" + recipient);
        System.out.println("Message:" + message);
        System.out.println("1.Send Message");
        System.out.println("2. Store Message");
        System.out.println("3.Disregard Message");
        System.out.println("Choose option:");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                sentMessages.add(this);
                saveToJSON();
                return "Message successfully sent";

            case 2:
                storeMessage();
                return "Message successfully stored.";

            case 3:
                return "Press 0 to delete the message";
            default:
                return "Invalid choice. Message disregarded";

        }
    }

    public String printMessages() {
        return "Message ID:" + messageID + "\nMessage Hash:" + messageHash + "\nRecipient:"
                + recipient + "\nMessage:" + message;

    }

    public int returnTotalMessagess() {
        return sentMessages.size();
    }

    public void storeMessage() {
        sentMessages.add(this);
        saveToJSON();
    }

    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    public String getMessageID() {
        return messageID;
    }

    private void saveToJSON() {
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(sentMessages, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON:" + e.getMessage());
        }
    }

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
