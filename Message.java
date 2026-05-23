/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp_registration_login;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Student
 */
public class Message {
    //VARIABLES
   private int messageID;
   private String recipientsCell;
   private String message;
   private static int totalMessages = 0;
   private static ArrayList<String> messageList = new ArrayList<>();
   
   //constructor 'it is a methodt runs automatically when a new message is created'
    public Message(String recipientsCell, String message){
        this.recipientsCell = recipientsCell;
        this.message = message;
    }
    
//method 1: generate a random message ID
public String checkMessageID() {
    Random random = new Random();
    messageID = random.nextInt(90) + 10;
    return String.valueOf(messageID);
}    
//method 2: check recipients cllphone number
public boolean checkRecipientsCell() {
    return recipientsCell.matches("\\+27[0-9]{9}");
}
//method 3: check message length
public String checkMessageLength() {
    if (message.length() <=250) {
        return "Message ready to send.";
    }else{
        int overBy = message.length() - 250;
        return "message exceeds 250 characters by " + overBy + ", f1please reduce size.";
    }
}
//method 4: send & store message
public String sentMessage() {
    if (message.length()<=250) {
        String msgEntry = "message ID: " + checkMessageID()
                        + "| To: " + recipientsCell
                        + "| message: " + message;
        messageList.add(msgEntry);
        totalMessages++;
        return "Messages successfully sent.";
    } else{
        return checkMessageLength();
            }
}
//method 5: print all sent messages
public void printMessages() {
    if (messageList.isEmpty()) {
        System.out.println("no messages sent yet.");
    }else {
        for (String msg : messageList) {
            System.out.println(msg);
        }
    }
}
//method 6: return total number of messages sent
public int returnTotalMessages() {
    return totalMessages;
}
//method 7: deletes a message
public String deleteMessage(String keyword) {
    for (int i = 0; i < messageList.size(); i++) {
        if (messageList.get(i).contains(keyword)) {
            messageList.remove(i);
            totalMessages--;
            return "message deleted successfully.";
        }
    }
    return "message not found.";
}

}
