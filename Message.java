/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp_registration_login;


/*
 * Message.java
 * Part 3 - Store Data and Display Task Report
 *
 * String manipulation is used to create the Message ID and Message Hash
 * using the loop counter (messageCounter) so they are unique and traceable.
 *
 * JSON file handling:
 * Stored messages are saved to "stored_messages.json" and can be loaded back.
 * Source: https://www.baeldung.com/java-org-json
 * org.json library used for JSON read/write.
 */

import java.util.ArrayList;
// JSON file read/write
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Message {

    // ===== INSTANCE VARIABLES =====
    private int    messageID;
    private String recipientsCell;
    private String message;
    private String flag;
    private String messageHash;

    // ===== STATIC COUNTER — used for ID and Hash generation =====
    // This is the loop counter the rubric requires
    private static int messageCounter = 0;
    private static int totalMessages  = 0;

    // ===== PART 3: FIVE PARALLEL ARRAYS =====
    private static ArrayList<String>  sentMessages         = new ArrayList<>();
    private static ArrayList<String>  disregardedMessages  = new ArrayList<>();
    private static ArrayList<String>  storedMessages       = new ArrayList<>();
    private static ArrayList<String>  messageHashList      = new ArrayList<>();
    private static ArrayList<Integer> messageIDList        = new ArrayList<>();

    // JSON file path
    private static final String JSON_FILE = "stored_messages.json";

    // ===== CONSTRUCTOR =====
    public Message(String recipientsCell, String message) {
        this.recipientsCell = recipientsCell;
        this.message        = message;
    }

    // ===== GETTERS =====
    public String getRecipientsCell() { return recipientsCell; }
    public String getMessage()        { return message; }
    public String getMessageHash()    { return messageHash; }
    public int    getMessageID()      { return messageID; }

    // ===== ORIGINAL METHODS (Parts 1 & 2) =====

    // Method 1: Generate Message ID using string manipulation + loop counter
    // Format: takes first 2 characters of message + loop counter number
    // e.g. message "Hello" at counter 3 → "He:3"
    public String checkMessageID() {
        messageCounter++;
        // String manipulation: substring to get first 2 chars of message
        String prefix = (message.length() >= 2)
                ? message.substring(0, 2).toUpperCase()
                : message.toUpperCase();
        // Combine prefix string + counter number using string concatenation
        String idString = prefix + ":" + messageCounter;
        // Store numeric part as int for searching
        this.messageID = messageCounter;
        return idString;
    }

    // Method 2: Check recipient's cell phone number
    public boolean checkRecipientsCell() {
        return recipientsCell.matches("\\+27[0-9]{9}");
    }

    // Method 3: Check message length
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int overBy = message.length() - 250;
            return "message exceeds 250 characters by " + overBy + ", please reduce size.";
        }
    }

    // Method 4: Send message (used by Part 2 menu option 1)
    public String sentMessage() {
        if (message.length() <= 250) {
            String msgEntry = "message ID: " + checkMessageID()
                    + " | To: " + recipientsCell
                    + " | message: " + message;
            sentMessages.add(msgEntry);
            totalMessages++;
            return "Messages successfully sent.";
        } else {
            return checkMessageLength();
        }
    }

    // Method 5: Print all sent messages
    public void printMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
        } else {
            for (String msg : sentMessages) {
                System.out.println(msg);
            }
        }
    }

    // Method 6: Return total number of messages sent
    public int returnTotalMessages() {
        return totalMessages;
    }

    // Method 7: Delete message by keyword (original)
    public String deleteMessage(String keyword) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).contains(keyword)) {
                sentMessages.remove(i);
                totalMessages--;
                return "message deleted successfully.";
            }
        }
        return "message not found.";
    }

    // ===== PART 3: NEW METHODS =====

    /**
     * Generate Message Hash using string manipulation + loop counter.
     * Takes the first word of the message + "Message" + counter number.
     * e.g. "Where are you..." at counter 2 → "WhereMessage2"
     * Source for String.split(): https://www.w3schools.com/java/ref_string_split.asp
     */
    private String generateHash() {
        // String manipulation: split on spaces, take first word
        String[] words = message.trim().split("\\s+");
        String firstWord = (words.length > 0) ? words[0] : "Msg";
        // Concatenate first word + "Message" + current counter number
        return firstWord + "Message" + messageCounter;
    }

    /**
     * Add a message to the correct array based on its flag.
     * Also generates the ID and Hash using the loop counter.
     * Flag options: "Sent", "Stored", "Disregard"
     */
    public String addMessageToArray(String flag) {
        // Capitalise first letter so "sent", "Sent", "SENT" all work
        String normFlag = flag.substring(0, 1).toUpperCase() + flag.substring(1).toLowerCase();
        this.flag = normFlag;

        // Generate ID first (this increments the counter)
        String idString = checkMessageID();

        // Generate hash using the updated counter
        this.messageHash = generateHash();

        // Add to parallel arrays
        messageHashList.add(messageHash);
        messageIDList.add(messageID);

        // Build the entry string
        String entry = "Hash: "    + messageHash
                + " | ID: "       + idString
                + " | To: "       + recipientsCell
                + " | Message: "  + message
                + " | Flag: "     + normFlag;

        // Route to correct array based on flag
        switch (normFlag) {
            case "Sent":
                sentMessages.add(entry);
                totalMessages++;
                return "Messages successfully sent.";
            case "Stored":
                storedMessages.add(entry);
                // Save to JSON file every time a message is stored
                saveStoredMessagesToJSON();
                return "Message successfully stored.";
            case "Disregard":
                disregardedMessages.add(entry);
                return "Message disregarded.";
            default:
                return "Unknown flag: " + flag;
        }
    }

    // ===== JSON FILE METHODS =====

    /**
     * Save all stored messages to a JSON file.
     * Each message is saved as a JSON object with hash, id, recipient, message, flag fields.
     * Source: https://www.baeldung.com/java-org-json
     */
    public static void saveStoredMessagesToJSON() {
        JSONArray jsonArray = new JSONArray();
        for (String entry : storedMessages) {
            JSONObject obj = new JSONObject();
            obj.put("hash",      extractField(entry, "Hash: ",       " | ID:"));
            obj.put("id",        extractField(entry, "| ID: ",       " | To:"));
            obj.put("recipient", extractField(entry, "| To: ",       " | Message:"));
            obj.put("message",   extractField(entry, "| Message: ",  " | Flag:"));
            obj.put("flag",      extractField(entry, "| Flag: ",     ""));
            jsonArray.put(obj);
        }
        // Write to file
        try (FileWriter file = new FileWriter(JSON_FILE)) {
            file.write(jsonArray.toString(4)); // 4 = indent spaces for readability
            System.out.println("Stored messages saved to " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }

    /**
     * Read stored messages from JSON file back into the storedMessages array.
     * Source: https://www.baeldung.com/java-org-json
     */
    public static void loadStoredMessagesFromJSON() {
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            System.out.println("No stored messages file found.");
            return;
        }
        // Read file content into a string
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            return;
        }
        // Parse JSON array and rebuild storedMessages array
        JSONArray jsonArray = new JSONArray(content.toString());
        storedMessages.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String entry = "Hash: "    + obj.getString("hash")
                    + " | ID: "       + obj.getString("id")
                    + " | To: "       + obj.getString("recipient")
                    + " | Message: "  + obj.getString("message")
                    + " | Flag: "     + obj.getString("flag");
            storedMessages.add(entry);
        }
        System.out.println("Loaded " + storedMessages.size() + " stored message(s) from " + JSON_FILE);
    }

    // ===== PART 3 MENU FEATURES =====

    // (a) Display sender and recipient of all stored messages
    public static void displayStoredSenderRecipient() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        System.out.println("\n--- Stored Messages: Sender & Recipient ---");
        for (String msg : storedMessages) {
            String recipient = extractField(msg, "| To: ",      " | Message:");
            String msgText   = extractField(msg, "| Message: ", " | Flag:");
            System.out.println("Recipient: " + recipient + " | Message: " + msgText);
        }
    }

    // (b) Display the longest message (searches parallel arrays)
    public static String getLongestMessage() {
        ArrayList<String> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            return "No messages available.";
        }
        String longest = "";
        // Loop through all messages to find the longest
        for (String entry : allMessages) {
            String msgText = extractField(entry, "| Message: ", " | Flag:");
            if (msgText.length() > longest.length()) {
                longest = msgText;
            }
        }
        return longest.isEmpty() ? "No messages found." : longest;
    }

    // (c) Search for a message by ID — returns recipient and message
    public static String searchByMessageID(int searchID) {
        ArrayList<String> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        for (String entry : allMessages) {
            // messageID is stored as numeric part in messageIDList
            // We search the ID field in the entry string
            String idField = extractField(entry, "| ID: ", " | To:");
            // ID string format is "XX:N" — the counter is after the colon
            if (idField.contains(":" + searchID)) {
                String recipient = extractField(entry, "| To: ",      " | Message:");
                String msgText   = extractField(entry, "| Message: ", " | Flag:");
                return "Recipient: " + recipient + " | Message: " + msgText;
            }
        }
        return "Message ID not found.";
    }

    // (d) Search all messages for a particular recipient (searches parallel arrays)
    public static String searchByRecipient(String cell) {
        ArrayList<String> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        StringBuilder result = new StringBuilder();
        for (String entry : allMessages) {
            if (entry.contains("| To: " + cell)) {
                String msgText = extractField(entry, "| Message: ", " | Flag:");
                result.append("\"").append(msgText).append("\" ");
            }
        }
        return result.length() > 0
                ? result.toString().trim()
                : "No messages found for that recipient.";
    }

    // (e) Delete a message using the message hash
    public static String deleteMessageByHash(String hash) {
        // Search sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).contains("Hash: " + hash + " ")) {
                String deleted = sentMessages.get(i);
                sentMessages.remove(i);
                totalMessages--;
                String msgText = extractField(deleted, "| Message: ", " | Flag:");
                return "Message: \"" + msgText + "\" successfully deleted.";
            }
        }
        // Search stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).contains("Hash: " + hash + " ")) {
                String deleted = storedMessages.get(i);
                storedMessages.remove(i);
                // Update JSON file after deletion
                saveStoredMessagesToJSON();
                String msgText = extractField(deleted, "| Message: ", " | Flag:");
                return "Message: \"" + msgText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // (f) Display full report — shows Message Hash, Recipient, Message for all messages
    public static void displayReport() {
        ArrayList<String> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            System.out.println("No messages to report.");
            return;
        }
        System.out.println("\n========== MESSAGE REPORT ==========");
        System.out.printf("%-22s %-18s %s%n", "Message Hash", "Recipient", "Message");
        System.out.println("--------------------------------------------------------------");
        for (String entry : allMessages) {
            String hash      = extractField(entry, "Hash: ",       " | ID:");
            String recipient = extractField(entry, "| To: ",       " | Message:");
            String msgText   = extractField(entry, "| Message: ",  " | Flag:");
            System.out.printf("%-22s %-18s %s%n", hash, recipient, msgText);
        }
        System.out.println("====================================");
    }

    // ===== HELPER METHOD =====
    // Extract text between two delimiters (string manipulation)
    private static String extractField(String entry, String startDelim, String endDelim) {
        int start = entry.indexOf(startDelim);
        if (start == -1) return "";
        start += startDelim.length();
        if (endDelim.isEmpty()) return entry.substring(start).trim();
        int end = entry.indexOf(endDelim, start);
        return (end != -1) ? entry.substring(start, end).trim() : entry.substring(start).trim();
    }

    // ===== ARRAY ACCESSORS (used by unit tests) =====
    public static ArrayList<String>  getSentMessages()         { return sentMessages; }
    public static ArrayList<String>  getStoredMessages()       { return storedMessages; }
    public static ArrayList<String>  getDisregardedMessages()  { return disregardedMessages; }
    public static ArrayList<String>  getMessageHashList()      { return messageHashList; }
    public static ArrayList<Integer> getMessageIDList()        { return messageIDList; }

    // Reset all arrays and counter (used in unit tests)
    public static void clearAllArrays() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashList.clear();
        messageIDList.clear();
        totalMessages    = 0;
        messageCounter   = 0;
    }
}
