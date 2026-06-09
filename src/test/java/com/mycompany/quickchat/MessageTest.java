/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import com.mycompany.quickchat.Message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ondwela Mathobo
 */
public class MessageTest {

    public MessageTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Clear sent messages before each test so tests don't interfere with each other
        Message.sentMessages.clear();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of checkMessageID method, of class Message.
     * Message ID should be no more than 10 characters
     */
    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        boolean result = instance.checkMessageID();
        assertTrue(result);
    }

    /**
     * Test of checkRecipientCell method, of class Message.
     * Tests both success and failure cases
     */
    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");

        // Test valid number starting with +27
        Message valid = new Message("+27718693002", "Test", 1);
        assertEquals("Cell phone number successfully captured.", valid.checkRecipientCell());

        // Test invalid number not starting with +27
        Message invalid = new Message("08575975889", "Test", 2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", invalid.checkRecipientCell());
    }

    /**
     * Test of createMessageHash method, of class Message.
     * Hash format should be: first2digitsOfID:numMessage:FIRSTWORDLASTWORD
     * Example: 00:0:HITONIGHT
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        String result = instance.createMessageHash();

        // Hash must contain :1: since numMessage is 1
        assertTrue(result.contains(":1:"));
        // Hash must end with first word + last word in caps
        assertTrue(result.endsWith("HITONIGHT?"));
    }

    /**
     * Test of SentMessage method, of class Message.
     * Tests send, discard and store options
     */
    @Test
    public void testSentMessage() {
        System.out.println("SentMessage");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);

        // Test "send" option
        String result1 = instance.SentMessage("send");
        assertEquals("Message successfully sent.", result1);

        // Test "discard" option
        Message instance2 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 2);
        String result2 = instance2.SentMessage("discard");
        assertEquals("Press 0 to delete the message", result2);

        // Test "store" option
        Message instance3 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 3);
        String result3 = instance3.SentMessage("store");
        assertEquals("Message successfully stored.", result3);
    }

    /**
     * Test of printMessages method, of class Message.
     * Should contain recipient and message in output
     */
    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        instance.SentMessage("send");

        String result = instance.printMessages();
        assertTrue(result.contains("+27718693002"));
        assertTrue(result.contains("Hi Mike, can you join us for dinner tonight?"));
    }

    /**
     * Test of returnTotalMessagess method, of class Message.
     * Should return total number of messages sent
     */
    @Test
    public void testReturnTotalMessagess() {
        System.out.println("returnTotalMessagess");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        instance.SentMessage("send");

        int result = instance.returnTotalMessagess();
        assertTrue(result >= 1);
    }

    /**
     * Test of storeMessage method, of class Message.
     * sentMessages list should grow by 1 after storing
     */
    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);

        int before = Message.sentMessages.size();
        instance.storeMessage();
        int after = Message.sentMessages.size();

        assertEquals(before + 1, after);
    }

    /**
     * Test of checkMessageLength method, of class Message.
     * Message must not exceed 250 characters
     */
    @Test
    public void testCheckMessageLength() {
        System.out.println("checkMessageLength");

        // Test case 1: under 250 chars - should pass
        Message instance1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        boolean result1 = instance1.checkMessageLength();
        assertTrue(result1);

        // Test case 2: over 250 chars - should fail
        String longMsg = "a".repeat(260);
        Message instance2 = new Message("+27718693002", longMsg, 2);
        boolean result2 = instance2.checkMessageLength();
        assertFalse(result2);
    }

    /**
     * Test of getMessageID method, of class Message.
     * Message ID should be exactly 10 characters long
     */
    @Test
    public void testGetMessageID() {
        System.out.println("getMessageID");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);

        String result = instance.getMessageID();
        assertEquals(10, result.length());
    }

}
//package com.mycompany.quickchat;
//
//import com.mycompany.quickchat.Message;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author Ondwela Mathobo
// */
//public class MessageTest {
//    
//    public MessageTest() {
//    }
//    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }
//
//    /**
//     * Test of main method, of class Message.
//     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Message.main(args);
//       
//    }
//
//    /**
//     * Test of checkMessageID method, of class Message.
//     */
//    @Test
//    public void testCheckMessageID() {
//        System.out.println("checkMessageID");
//        System.out.println("getMessageID");
//    Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    
//    String expResult = "01:HITONIG"; // 01 + first 7 chars of HITONIGHT
//    String result = instance.getMessageID();
//    assertEquals(expResult, result); 
//        
//    }
//
//    /**
//     * Test of checkRecipientCell method, of class Message.
//     */
//    @Test
//    public void testCheckRecipientCell() {
//        System.out.println("checkRecipientCell");
//        Message valid = new Message("+27718693002", "Test", 1);
//    assertEquals("Cell phone number successfully captured.", valid.checkRecipientCell());
//    
//    Message invalid = new Message("08575975889", "Test", 2);
//    assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", invalid.checkRecipientCell());
//       
//    }
//
//    /**
//     * Test of createMessageHash method, of class Message.
//     */
//    @Test
//    public void testCreateMessageHash() {
//        System.out.println("createMessageHash");
//       Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    String expResult = "00:HITONIGHT";
//    String result = instance.createMessageHash();
//    assertEquals(expResult, result);
//        
//    }
//
//    /**
//     * Test of SentMessage method, of class Message.
//     */
//    @Test
//    public void testSentMessage() {
//     Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    
//    // Test "send" option
//    String result1 = instance.SentMessage("send");
//    assertEquals("Message successfully sent.", result1);
//    
//    // Test "discard" option  
//    String result2 = instance.SentMessage("discard");
//    assertEquals("Message discarded.", result2);   
//        
//    }
//
//    /**
//     * Test of printMessages method, of class Message.
//     */
//    @Test
//    public void testPrintMessages() {
//        System.out.println("printMessages");
//       Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    instance.SentMessage("send"); // add a message first
//    
//    String result = instance.printMessages();
//    assertTrue(result.contains("+27718693002"));
//    assertTrue(result.contains("Hi Mike, can you join us for dinner tonight?"));
//    }
//
//    /**
//     * Test of returnTotalMessagess method, of class Message.
//     */
//    @Test
//    public void testReturnTotalMessagess() {
//        System.out.println("returnTotalMessagess");
//         Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    instance.storeMessage(); // add one message first
//    
//    int expResult = 1;
//    int result = instance.returnTotalMessagess();
//    assertEquals(expResult, result);
//        
//    }
//
//    /**
//     * Test of storeMessage method, of class Message.
//     */
//    @Test
//    public void testStoreMessage() {
//        System.out.println("storeMessage");
//        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    
//    int before = Message.sentMessages.size();
//    instance.storeMessage();
//    int after = Message.sentMessages.size();
//    
//    assertEquals(before + 1, after);
//}
//        
//    }
//
//    /**
//     * Test of checkMessageLength method, of class Message.
//     */
//    @Test
//    public void testCheckMessageLength() {
//        System.out.println("checkMessageLength");
//      // Test case 1: under 250 chars - should pass
//    Message instance1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    boolean result1 = instance1.checkMessageLength();
//    assertTrue(result1);
//    
//    // Test case 2: over 250 chars - should fail
//    String longMsg = "a".repeat(260);
//    Message instance2 = new Message("+27718693002", longMsg, 2);
//    boolean result2 = instance2.checkMessageLength();
//    assertFalse(result2);  
//        
//    }
//
//    /**
//     * Test of getMessageID method, of class Message.
//     */
//    @Test
//    public void testGetMessageID() {
//        System.out.println("getMessageID");
//     Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
//    
//    String expResult = "01:HITONIG"; // 01 + first 7 chars of HITONIGHT
//    String result = instance.getMessageID();
//    assertEquals(expResult, result);    
//        
//       
//    }
//    

