package Views;

import java.util.Scanner;

import Controllers.AdminController;
import Controllers.UserController;
import Enumerations.Faculty;
import Enumerations.SemesterType;
import Enumerations.UserType;
import Exception.UserNotFoundException;
import Utils.News;

public class AdminView {
	
	private static Scanner in = new Scanner(System.in);
//	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	//admin welcome page
	public static void welcome() throws UserNotFoundException {
		System.out.println("Welcome to WSP!\n<ADMIN SIDE>\nPlease select the option:");
		System.out.println("- 0.Exit"); 
		System.out.println("- 1. Create a new user;");
		System.out.println("- 2. Show teachers;");
		System.out.println("- 3. Show students;");
		System.out.println("- 4. Show managers"); 
		System.out.println("- 5. Delete a user"); 
		System.out.println("- 6.See all news"); 
		System.out.print("\nOption : ");
		int option = in.nextInt();
		if(option == 0) {
			System.out.println("Bye bye!"); 
			MainView.welcome();
		}
		else if (option == 1) {
			createUser();
		} else if (option == 2) {
			showTeachers();
		} else if (option == 3) {
			showStudents(); 
		} else if(option == 4) {
			showManagers(); 
		} else if(option == 5) {
			deleteUser();
		} else if(option == 6) {
			seeNews();
		}
	}
    public static void seeNews() {
        System.out.println("Loading all news...");

        for (News news : UserController.getAllNews()) {
            System.out.println(news);
            System.out.println("------------------------");
        }
    }
	public static void showManagers() throws UserNotFoundException {
		System.out.println("Loading the managers..."); 
		
		Object o = UserController.getUsers(UserType.MANAGER);
		
		System.out.println(o);
		
		welcome();
	}
	public static void showTeachers() throws UserNotFoundException {
		System.out.println("Loading the teachers...");
		
		Object o = UserController.getUsers(UserType.TEACHER);
		
		System.out.println(o);
		
		welcome();
	}
	public static void showStudents() throws UserNotFoundException {
		System.out.println("Loading the students..."); 
		
		Object o = UserController.getUsers(UserType.STUDENT); 
		
		System.out.println(o) ;
		
		welcome() ; 
	}
	public static void createUser() throws UserNotFoundException {
		
		System.out.println("What role do you want to create?");
		System.out.println("- 0. Back to Admin side"); 
		System.out.println("- 1. Teacher;");
		System.out.println("- 2. Student;");
		System.out.println("- 3. Manager;");
		System.out.print("\n Option : ");
		int option = in.nextInt();
		
		in.nextLine(); // waiting moment
		if(option == 0) {
			System.out.println("TO ADMIN SIDE ..."); 
			welcome(); 
		}
		else if (option == 1) {
			System.out.println("Creating a new teacher...");
			
			System.out.print("Enter a username:");
			String username = in.nextLine();
			System.out.println(); 
			System.out.print("Enter a password:");
			String password = in.nextLine();
			
			// controller
			boolean res = AdminController.addUser(username, password  , UserType.TEACHER, null, option);
			
			if (res) {
				System.out.println(username + " is created!");
			} else {
				System.err.println("error!");
			}
			welcome();
		}
		else if (option == 2) {
			System.out.println("Creating a new student..."); 
			System.out.println("Enter a username:");
			String username = in.nextLine();
			System.out.println("Enter a password:");
			String password = in.nextLine();
			System.out.println("Select faculty"); 
			System.out.println("1.Fit"); 
			System.out.println("2.BS"); 
			System.out.println("3.Sepi"); 
			System.out.println("Options"); 
			int facultyoption = in.nextInt();
			
			Faculty faculty = null;

		    switch (facultyoption) {
		    	case 1:
		    		faculty = Faculty.FIT;
		    		break;
		    	case 2:
		    		faculty = Faculty.BS;
		    		break;
		    	case 3:
		    		faculty = Faculty.SEPI;
		    		break;
		    	default:
		    		System.out.println("Invalid faculty options");
		    		return;
		    }
		    
	        System.out.println("Select study year:");
	        System.out.println("1. First Year");
	        System.out.println("2. Second Year");
	        System.out.println("3. Third Year");
	        System.out.println("4. Fourth Year");
	        System.out.print("Option: ");
	        int yearOption = in.nextInt();
	        int studyYear = 0;

	        switch (yearOption) {
	            case 1:
	                studyYear = 1;
	                break;
	            case 2:
	                studyYear = 2;
	                break;
	            case 3:
	                studyYear = 3;
	                break;
	            case 4:
	                studyYear = 4;
	                break;
	            default:
	                System.out.println("Invalid year option");
	                return;  // Выход из метода при неверном выборе
	        }

			
			boolean res = AdminController.addUser(username, password,UserType.STUDENT, faculty, studyYear ); 
			
			if(res) {
				System.out.println(username + " is created!"); 
			}
			else {
				System.out.println("error!"); 
			}
			welcome() ;
		}
		else if(option == 3) {
			System.out.println("Creating a new manager...");
			System.out.println("Enter a username:");
			String username = in.nextLine();
			System.out.println("Enter a password:");
			String password = in.nextLine();

			boolean res = AdminController.addUser(username, password, UserType.MANAGER,null,option); 
			
			if(res) {
				System.out.println(username + " is created!"); 
			}
			else {
				System.out.println("error!"); 
			}
			welcome() ;
		}
	
	}
	public static void deleteUser() throws UserNotFoundException {
		 System.out.println("What type of user do you want to delete?");
	     System.out.println("- 0.Back to ADMIN SIDE"); 
		 System.out.println("- 1. Teacher");
	     System.out.println("- 2. Student");
	     System.out.println("- 3. Manager");
	     System.out.print("\nOption :");
	     int option = in.nextInt(); 
	     if(option == 0) {
	    	 System.out.println("TO ADMIN SIDE..."); 
	    	 welcome(); 
	     }
	     else {
	    	 in.nextLine(); 
	     	System.out.println("Enter the username of the user to delete");
	     	String username = in.nextLine(); 
	     
	     	UserType type = option == 1 ? UserType.TEACHER : option == 2 ? UserType.STUDENT : UserType.MANAGER; 
	     	boolean res = AdminController.deleteUser(username, type); 
	     	if(res) {
	    	 	System.out.println(username + " has been deleted!") ;
	     	}
	     	else {
	    	 	System.out.println("Error : USER NOT FOUND!"); 
	     	}
	     	welcome() ; 
	     }
	}
}