/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp_registration_login;

import java.util.Scanner;

/**
 *
 * @author Student this is just for testing
 */
class chat{
    //global variables(datafields)
    String storedusername;
    String storedpassword;
    String storedcellPhoneNumber;
    String storedFirstName;
    String storedLastName;
    Scanner inputuser=new Scanner (System.in);
    
    
    //creating a return method to check username validation
    boolean checkuserName(String userName){
        //if it contains a underscore
        //should not be more than 5 characters.
        //conditional statement.
        if(userName.contains( "_") && (userName.length() <=5)){
            return true;
        }else{
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
            return false;
        }
         
    }
    //creating a return method to check password validation
    boolean checkPasswordComplexity(String password){
        //length()-checks numbers of characters
        //matches()-checks if all characters are numbers
        //atleast 8 characters long
        //contains a capitla letter,a number,a spcial character
        if(password.length()>=8 
                && password.matches(".*[A-Z].*") 
                && password.matches(".*[0-9].*") 
                && password.matches(".*[^A-Za-z0-9].*")){
                        
            return true;
        }else{
             System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }        
    }
    //creating a return method to check cellPhoneNumber validation
    boolean checkcellPhoneNumber(String cellPhoneNumber){
         //length()-checks numbers of characters
         //matches()-checks if all characters are numbers
        String regex = "^\\+27\\d{1,10}$";
        return cellPhoneNumber . matches(regex);
    }
    //creating a method called registerUser
    String registerUser(){
        System.out.println("====REGISTER====");
        System.out.println("Enter first name: ");
        storedFirstName = inputuser.nextLine();
        System.out.println("Enter last name: ");
        storedLastName = inputuser.nextLine();
        System.out.println("Enter username");
        storedusername=inputuser.nextLine();
        System.out.println("Enter password");
        storedpassword=inputuser.nextLine();
        System.out.println("Enter cellphone nummber");
        storedcellPhoneNumber=inputuser.nextLine();
        
        //if all conditions are true
        if (checkuserName(storedusername) && checkPasswordComplexity(storedpassword) && checkcellPhoneNumber(storedcellPhoneNumber)){
            return("registration successfull!!!");
        }else{
            return("registration failed. try again");
        }
     }
    //creating a method called loginUser
    boolean loginUser(String userName,String password){
        
        // check if user has registered first
    if (storedusername == null || storedpassword == null) {
        System.out.println("Please register first before logging in.");
        return false;
    }
        if (storedusername.equals(userName) && storedpassword.equals(password)){
            return true;
        } else{
            return false;
        }
    }
    //creating a method called returnLoginStatus
    String returnLoginStatus(String username,String password){ //maar this one is not make sure ,ill check it later dah.
     //return welcome message or failure message
     if (loginUser(username, password)){
         return "Welcome " + storedFirstName + "," + storedLastName +" it is great to see you again.";
     }else{
         return "username or password inncorrect, please try again.";
     }
        
     }
}     
/**
 *
 * @author Student
 */
