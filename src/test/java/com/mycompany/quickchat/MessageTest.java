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
        Message.storedMessages.clear();
        Message.disregardedMessages.clear();
        Message.messageHashArray.clear();
        Message.messageIDArray.clear();
    }
@AfterEach
    public void tearDown() {
    }

    // ── Part 2 tests ─────────────────────────────────────────────────────────

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
     * Hash format: first2digitsOfID:numMessage:FIRSTWORDLASTWORD
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

        // Test "send" option
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
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
     * Should contain recipient, message and message hash in output
     */
    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        instance.SentMessage("send");

        String result = instance.printMessages();
        assertTrue(result.contains("+27718693002"));
        assertTrue(result.contains("Hi Mike, can you join us for dinner tonight?"));
        // Check that message hash is included in output
        assertTrue(result.contains("Message Hash:"));
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
     * storedMessages list should grow by 1 after storing
     */
    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);

        int before = Message.storedMessages.size();
        instance.storeMessage();
        int after = Message.storedMessages.size();

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

    // ── Part 3 tests ─────────────────────────────────────────────────────────

    /**
     * Test that sent messages array is correctly populated.
     * Uses Part 3 test data messages 1 and 4 (both flagged Sent)
     */
    @Test
    public void testSentMessagesArrayPopulated() {
        System.out.println("sentMessagesArrayPopulated");

        // Test data message 1 - Sent
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.SentMessage("send");

        // Test data message 4 - Sent (developer entry, bypasses +27 check for testing)
        Message msg4 = new Message("0838884567", "It is dinner time!", 4);
        msg4.SentMessage("send");

        // Sent array should contain both messages
        assertEquals(2, Message.sentMessages.size());
        assertEquals("Did you get the cake?", Message.sentMessages.get(0).getMessage());
        assertEquals("It is dinner time!", Message.sentMessages.get(1).getMessage());
    }

    /**
     * Test that the longest message is correctly identified.
     * Uses Part 3 test data - message 2 is the longest
     */
    @Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");

        // Load Part 3 test data
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.SentMessage("send");

        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.SentMessage("store");

        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3);
        msg3.SentMessage("discard");

        String result = Message.displayLongestMessage();
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
    }

    /**
     * Test search by message ID.
     * Should return the correct recipient and message
     */
    @Test
    public void testSearchByMessageID() {
        System.out.println("searchByMessageID");

        Message msg4 = new Message("0838884567", "It is dinner time!", 4);
        msg4.SentMessage("send");

        // Search using the actual generated ID
        String id = msg4.getMessageID();
        String result = Message.searchByMessageID(id);

        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains("0838884567"));
    }

    /**
     * Test search by recipient.
     * +27838884567 has 2 messages 
     */
    @Test
    public void testSearchByRecipient() {
        System.out.println("searchByRecipient");

        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.SentMessage("store");

        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 5);
        msg5.SentMessage("store");

        String result = Message.searchByRecipient("+27838884567");

        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    /**
     * Test delete message by hash.
     * Should remove the message and return success message
     */
    @Test
    public void testDeleteMessage() {
        System.out.println("deleteMessage");

        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.SentMessage("store");

        String hash = msg2.getMessageHash();
        String result = Message.deleteMessage(hash);

        assertTrue(result.contains("successfully deleted."));
        // Confirm message is removed from storedMessages
        assertEquals(0, Message.storedMessages.size());
    }

    /**
     * Test display report.
     * Should show message hash, recipient and message for all sent messages
     */
    @Test
    public void testDisplayReport() {
        System.out.println("displayReport");

        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.SentMessage("send");

        Message msg4 = new Message("0838884567", "It is dinner time!", 4);
        msg4.SentMessage("send");

        String result = Message.displayReport();

        // Report must contain hash, recipient and message
        assertTrue(result.contains("Message Hash:"));
        assertTrue(result.contains("+27834557896"));
        assertTrue(result.contains("Did you get the cake?"));
    }
}
