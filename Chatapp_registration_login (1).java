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
    }else {
            System.out.println("please ensure that your registration was successfull first");
}
    
     
}}
