package Views;

import java.util.Scanner;

import Controllers.ManagerController;
import Controllers.UserController;
import Utils.News;
import Utils.Course;

public class ManagerView {
private static Scanner in = new Scanner(System.in);
    

    public static void welcome() {
        System.out.println("Welcome to WSP!\n<MANAGER SIDE>\nPlease select the option:");
        System.out.println("- 0. Exit");
        System.out.println("- 1. Create news");
        System.out.println("- 2. See all news");
        System.out.println("- 3. Create course");;
        System.out.println("- 4. See all course");
        System.out.print("\nOption: ");
        int option = in.nextInt();
        
        if(option == 0) {
            System.out.println("Bye bye!");
            MainView.welcome();
        }
        else if(option == 1) {
            createNews();
        } else if(option == 2) {
            seeNews();
        } else if (option == 3){
        	createcourse();
        }
          else if (option == 4){
        	  seecourse();
          }
          else
        {
            System.out.println("Invalid option, try again.");
            welcome();
        }
    }

    
    public static void createNews() {
        in.nextLine(); 
        
        System.out.println("Enter the news title:");
        String title = in.nextLine();
        
        System.out.println("Enter the news content:");
        String content = in.nextLine();
        
        News news = new News(title, content);
        
        
       
        boolean res = ManagerController.createNews(news);
        
        if(res) {
            System.out.println("News has been created successfully!");
        } else {
            System.out.println("Error creating news.");
        }
        
        welcome();
    }
    
    public static void createcourse() {
        in.nextLine();
        
        System.out.println("Enter the Course Id:");
        String courseId = in.nextLine();
        
        System.out.println("Enter the course name:");
        String courseName = in.nextLine();
        
        System.out.println("Enter the course teacher:");
        String courseTeacher = in.nextLine();
       
        
		Course course = new Course(courseId, courseName, courseTeacher);
        
       
        boolean res = ManagerController.createcourse(course);
        
        if(res) {
            System.out.println("Course has been created successfully!");
        } else {
            System.out.println("Error creating course.");
        }
        
        welcome();
    }
    
    
    public static void seeNews() {
        System.out.println("Loading all news...");
        
        for (News news : UserController.getAllNews()) {
            System.out.println(news);
            System.out.println("------------------------");
        }
   
        welcome();
    }
    public static void seecourse() {
    	System.out.println("Loading all course");
    	
    	for (Course course : UserController.getAllCourses()) {
            System.out.println(course);
            System.out.println("------------------------");
        }
   
        welcome();
    }
}
