/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapp_registration_login;

import java.util.Scanner;

public class Chatapp_registration_login {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //input
        
        
        
        chat user = new chat();
        Scanner inputuser = new Scanner(System.in);
        
        //The user register here first
        user.registerUser();
        
        //process + output
        // only go to login if registration was successful

     if (user.storedusername != null) {   
        System.out.println("====login====");
        System.out.println("Enter username:");
        String loginUser = inputuser.nextLine();
        System.out.println("Enter password");
        String loginPass = inputuser.nextLine();
        
        System.out.println(user.returnLoginStatus(loginUser, loginPass));
     if (user.loginUser(loginUser, loginPass)) {
         int choice = 0;
         while (choice != 3) {
             System.out.println("\n===MESSAGE MENU===");
             System.out.println("1. Send message");
             System.out.println("2. Show sent message");
             System.out.println("3. Quit");
             System.out.print("Enter choice: ");
             choice = Integer.parseInt(inputuser.nextLine());
             
             if (choice == 1) {
                 System.out.print("Enter recipients cellphone number: ");
                 String recipientsCell = inputuser.nextLine();
                 System.out.print("Enter message: ");
                 String messageText = inputuser.nextLine();
                 Message msg = new Message(recipientsCell, messageText);
                 System.out.println(msg.sentMessage());
             
             }else if (choice == 2) {
                 Message tempMsg = new Message("", "");
                 tempMsg.printMessages();
                 
             }else if (choice == 3) {
                 System.out.println("Total messages sent" + new Message("","").returnTotalMessages());
                 System.out.println("Goodbye!");
             }
         }
     }
     }else {
            System.out.println("please ensure that your registration was successfull first");
}
 
   
}}
