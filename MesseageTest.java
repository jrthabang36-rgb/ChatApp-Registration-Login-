/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp_registration_login;
 
import org.junit.Test;
import static org.junit.Assert.*;

public class MesseageTest {
    
    //TEST 1: Check if mesage ID is a valid 2-digit number
    @Test
    public void testCheckMessageID(){
        Message msg = new Message("+27821234567","Hello there");
        int id = Integer.parseInt(msg.checkMessageID());
        assertTrue(id >= 10 && id <=99);
    }
    //TEST 2: Message over 250 characters should be rejected
    @Test
    public void testMessageTooLong() {
        String longMessage = "A".repeat(251);
        Message msg = new Message("+27821234567", longMessage);
        String result = msg.checkMessageLength();
        assertEquals("message exceeds 250 characters by 1, please reduce size.", result);
    }
    //TEST 3: Message under 250 characters should be accepted 
    @Test
    public void testMessageCorrectLength() {
        Message msg = new Message("+27821234567","hello this is a short message");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }
    //TSET 4: Message should be sent successfulkly
    @Test
    public void testSentMessage() {
        Message msg = new Message("+27821234567","Hello there");
        String result = msg.sentMessage();
        assertEquals("Messages successfully sent.", result);
    }
}
