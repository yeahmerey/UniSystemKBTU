package Views;

import java.util.Scanner;
import Users.*;
import Controllers.UserController;
import Enumerations.UserType;
import Exception.UserNotFoundException;

public class MainView {
    private static Scanner in = new Scanner(System.in);

    // This will store the logged-in student (for later use)
    private static Student loggedInStudent;

    public static void welcome() {
        System.out.println("Welcome to WSP!\nSelect your role:");
        System.out.println("0. Admin");
        System.out.println("1. Teacher");
        System.out.println("2. Manager");
        System.out.println("3. Student");
        System.out.println("4. Finance Manager");
        
        System.out.print("\nRole: ");
        int option = in.nextInt();

        try {
            switch (option) {
                case 0:
                    authorizeAdmin();
                    break;
                case 1:
                    authorize("teacher.txt", UserType.TEACHER);
                    break;
                case 2:
                    authorize("managers.txt", UserType.MANAGER);
                    break;
                case 3:
                    authorize("students.txt", UserType.STUDENT);
                    break;
                case 4:
                    authorizeFinanceManager();
                    break;
                default:
                    System.out.println("Invalid option!");
                    welcome();
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());  // Print the error message
            welcome();  // Show the welcome screen again for retry
        }
    }

    
    public static void authorizeFinanceManager() throws UserNotFoundException {
    	System.out.println("Please, enter admin credentials:");
        in.nextLine();  
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        FinanceManager admin = FinanceManager.getInstance();
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            System.out.println("Finance manager login successful!");
            FinanceManagerView.welcome();
        } else {
            System.out.println("Invalid username or password, TRY AGAIN:(!");
            welcome();
        }
    }

    public static void authorizeAdmin() throws UserNotFoundException {
        System.out.println("Please, enter admin credentials:");
        in.nextLine();  
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        Admin admin = Admin.getInstance();
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful!");
            AdminView.welcome();
        } else {
            System.out.println("Invalid username or password, TRY AGAIN:(!");
            welcome();
        }
    }

    public static void authorize(String fileName, UserType userType) throws UserNotFoundException {
        System.out.println("Please, enter your credentials:");
        in.nextLine();  // Consume the newline character left by nextInt()

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        boolean res = UserController.authorize(fileName, username, password); 
        if (res) {
            System.out.println("Login successful!");

            if (userType == UserType.STUDENT) {
                loggedInStudent = UserController.getStudentByUsername(username);
                StudentView.welcome(loggedInStudent);  
            } else if (userType == UserType.TEACHER) {
                Teacher loggedInTeacher = UserController.getTeacherByUsername(username); 
                TeacherView.welcome(loggedInTeacher);
            } else if (userType == UserType.MANAGER) {
                ManagerView.welcome(); 
            }
        } else {
            throw new UserNotFoundException("Invalid username or password.");
        }

    }
}
