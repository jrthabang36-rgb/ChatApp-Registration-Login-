/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapp_registration_login;

/*
 * Chatapp_registration_login.java
 * Main class — Parts 1, 2, and 3
 *
 * Part 3 adds:
 *  - A for loop to enter a set number of messages
 *  - Menu option 3: Stored Messages with sub-menu (a–f)
 *  - JSON file load on startup
 */

import java.util.Scanner;

public class Chatapp_registration_login {

    public static void main(String[] args) {

        // ===== INPUT =====
        chat user = new chat();
        Scanner inputuser = new Scanner(System.in);

        // Load any previously stored messages from JSON file on startup
        Message.loadStoredMessagesFromJSON();

        // Register user
        user.registerUser();

        // ===== PROCESS + OUTPUT =====
        if (user.storedusername != null) {
            System.out.println("====login====");
            System.out.println("Enter username:");
            String loginUser = inputuser.nextLine();
            System.out.println("Enter password");
            String loginPass = inputuser.nextLine();

            System.out.println(user.returnLoginStatus(loginUser, loginPass));

            if (user.loginUser(loginUser, loginPass)) {

                int choice = 0;
                while (choice != 4) {
                    System.out.println("\n===MESSAGE MENU===");
                    System.out.println("1. Send messages");
                    System.out.println("2. Show sent messages");
                    System.out.println("3. Stored Messages");
                    System.out.println("4. Quit");
                    System.out.print("Enter choice: ");
                    choice = Integer.parseInt(inputuser.nextLine());

                    // ---- Option 1: Send Messages (FOR LOOP) ----
                    if (choice == 1) {

                        // Ask user how many messages they want to send
                        System.out.print("How many messages would you like to send? ");
                        int numMessages = Integer.parseInt(inputuser.nextLine());

                        // FOR LOOP — runs exactly numMessages times as required by rubric
                        for (int i = 1; i <= numMessages; i++) {
                            System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

                            System.out.print("Enter recipient's cell number: ");
                            String recipientsCell = inputuser.nextLine();

                            System.out.print("Enter message: ");
                            String messageText = inputuser.nextLine();

                            System.out.print("Enter flag (Sent / Stored / Disregard): ");
                            String flag = inputuser.nextLine();

                            Message msg = new Message(recipientsCell, messageText);

                            // Validate message length only — cell number warning shown but message still added
                            if (!msg.checkRecipientsCell()) {
                                System.out.println("Cell number not in +27 format — please use +27XXXXXXXXX.");
                            }

                            if (msg.checkMessageLength().startsWith("message exceeds")) {
                                System.out.println(msg.checkMessageLength());
                            } else {
                                // Add to correct array — ID and Hash generated inside
                                System.out.println(msg.addMessageToArray(flag));
                                System.out.println("Message Hash: " + msg.getMessageHash());
                                System.out.println("Message ID:   " + msg.getMessageID());
                            }
                        }

                    // ---- Option 2: Show Sent Messages ----
                    } else if (choice == 2) {
                        Message tempMsg = new Message("", "");
                        tempMsg.printMessages();

                    // ---- Option 3: Stored Messages Sub-Menu ----
                    } else if (choice == 3) {
                        String sub = "";
                        while (!sub.equals("6")) {
                            System.out.println("\n--- STORED MESSAGES MENU ---");
                            System.out.println("a. Display sender and recipient of all stored messages");
                            System.out.println("b. Display the longest message");
                            System.out.println("c. Search for a message by ID");
                            System.out.println("d. Search messages for a particular recipient");
                            System.out.println("e. Delete a message using message hash");
                            System.out.println("f. Display full report");
                            System.out.println("g. Load stored messages from JSON file");
                            System.out.println("6. Back to main menu");
                            System.out.print("Enter choice: ");
                            sub = inputuser.nextLine().trim().toLowerCase();

                            switch (sub) {
                                case "a":
                                    Message.displayStoredSenderRecipient();
                                    break;
                                case "b":
                                    System.out.println("Longest message: " + Message.getLongestMessage());
                                    break;
                                case "c":
                                    System.out.print("Enter message ID number to search: ");
                                    int searchID = Integer.parseInt(inputuser.nextLine());
                                    System.out.println(Message.searchByMessageID(searchID));
                                    break;
                                case "d":
                                    System.out.print("Enter recipient cell number (e.g. +27838884567): ");
                                    String cell = inputuser.nextLine();
                                    System.out.println(Message.searchByRecipient(cell));
                                    break;
                                case "e":
                                    System.out.print("Enter message hash to delete: ");
                                    String hash = inputuser.nextLine();
                                    System.out.println(Message.deleteMessageByHash(hash));
                                    break;
                                case "f":
                                    Message.displayReport();
                                    break;
                                case "g":
                                    Message.loadStoredMessagesFromJSON();
                                    break;
                                case "6":
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please enter a, b, c, d, e, f, g or 6.");
                            }
                        }

                    // ---- Option 4: Quit ----
                    } else if (choice == 4) {
                        System.out.println("Total messages sent: " + new Message("", "").returnTotalMessages());
                        System.out.println("Goodbye!");
                    }
                }
            }
        } else {
            System.out.println("Please ensure that your registration was successful first.");
        }
    }
}
