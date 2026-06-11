
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp_registration_login;
 
/*
 * MesseageTest.java
 * Unit Tests for Parts 2 and 3
 * (filename kept as original — MesseageTest)
 */


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MesseageTest {

    // Reset all arrays and counter before EACH test
    // This ensures tests do not interfere with each other
    @Before
    public void setUp() {
        Message.clearAllArrays();
    }

    // ===========================
    // ORIGINAL TESTS (Part 2)
    // ===========================

    // TEST 1: Message ID should be a valid string containing a colon separator
    @Test
    public void testCheckMessageID() {
        Message msg = new Message("+27821234567", "Hello there");
        String id = msg.checkMessageID();
        // ID format is "XX:N" — must contain a colon
        assertTrue("Message ID should contain a colon separator", id.contains(":"));
    }

    // TEST 2: Message over 250 characters should be rejected
    @Test
    public void testMessageTooLong() {
        String longMessage = "A".repeat(251);
        Message msg = new Message("+27821234567", longMessage);
        String result = msg.checkMessageLength();
        assertEquals("message exceeds 250 characters by 1, please reduce size.", result);
    }

    // TEST 3: Message under 250 characters should be accepted
    @Test
    public void testMessageCorrectLength() {
        Message msg = new Message("+27821234567", "hello this is a short message");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    // TEST 4: Message should be sent successfully
    @Test
    public void testSentMessage() {
        Message msg = new Message("+27821234567", "Hello there");
        String result = msg.sentMessage();
        assertEquals("Messages successfully sent.", result);
    }

    // ===========================
    // PART 3 TESTS
    // ===========================

    /**
     * TEST 5: Sent Messages array correctly populated.
     * Developer entry for test data messages 1–4.
     * The system returns: "Did you get the cake?", "It is dinner time !"
     */
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Test Data Message 1 — Flag: Sent
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.addMessageToArray("Sent");

        // Test Data Message 2 — Flag: Stored
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.addMessageToArray("Stored");

        // Test Data Message 3 — Flag: Disregard
        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        msg3.addMessageToArray("Disregard");

        // Test Data Message 4 — Developer number, Flag: Sent
        Message msg4 = new Message("0838884567", "It is dinner time !");
        msg4.addMessageToArray("Sent");

        // Sent array must contain messages 1 and 4
        boolean containsMsg1 = false;
        boolean containsMsg4 = false;
        for (String entry : Message.getSentMessages()) {
            if (entry.contains("Did you get the cake?")) containsMsg1 = true;
            if (entry.contains("It is dinner time !"))  containsMsg4 = true;
        }
        assertTrue("Sent array should contain 'Did you get the cake?'", containsMsg1);
        assertTrue("Sent array should contain 'It is dinner time !'",   containsMsg4);
    }

    /**
     * TEST 6: Display the longest message.
     * Test Data: messages 1–4.
     * The system returns: "Where are you? You are late! I have asked you to be on time."
     */
    @Test
    public void testDisplayLongestMessage() {
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.addMessageToArray("Sent");

        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.addMessageToArray("Stored");

        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        msg3.addMessageToArray("Disregard");

        Message msg4 = new Message("0838884567", "It is dinner time !");
        msg4.addMessageToArray("Sent");

        String longest = Message.getLongestMessage();
        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            longest
        );
    }

    /**
     * TEST 7: Search for messageID.
     * Test Data: message 4 — developer "0838884567", message "It is dinner time !"
     * The system returns: "It is dinner time !"
     */
    @Test
    public void testSearchByMessageID() {
        Message msg4 = new Message("0838884567", "It is dinner time !");
        msg4.addMessageToArray("Sent");
        int storedID = msg4.getMessageID();

        String result = Message.searchByMessageID(storedID);
        assertTrue("Search result should contain 'It is dinner time !'",
                result.contains("It is dinner time !"));
    }

    /**
     * TEST 8: Search all messages for a particular recipient.
     * Test Data: +27838884567
     * The system returns:
     *   "Where are you? You are late! I have asked you to be on time."
     *   "Ok, I am leaving without you."
     */
    @Test
    public void testSearchByRecipient() {
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.addMessageToArray("Sent");

        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.addMessageToArray("Stored");

        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        msg3.addMessageToArray("Disregard");

        Message msg4 = new Message("0838884567", "It is dinner time !");
        msg4.addMessageToArray("Sent");

        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.");
        msg5.addMessageToArray("Stored");

        String result = Message.searchByRecipient("+27838884567");
        assertTrue("Result should contain message 2",
                result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue("Result should contain message 5",
                result.contains("Ok, I am leaving without you."));
    }

    /**
     * TEST 9: Delete a message using a message hash.
     * Test Data: Test Message 2 hash.
     * The system returns:
     *   Message: "Where are you? You are late! I have asked you to be on time" successfully deleted.
     */
    @Test
    public void testDeleteMessageByHash() {
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.addMessageToArray("Stored");
        String hash = msg2.getMessageHash();

        String result = Message.deleteMessageByHash(hash);
        assertTrue("Result should confirm deletion",
                result.contains("successfully deleted"));

        // Verify message is no longer in the array
        String search = Message.searchByRecipient("+27838884567");
        assertFalse("Deleted message should no longer appear",
                search.contains("Where are you? You are late!"));
    }
}
