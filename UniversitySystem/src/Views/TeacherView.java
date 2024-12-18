package Views;

import java.util.Scanner;
import Controllers.UserController;
import DLL.DBContext;
import Utils.Course;
import Utils.Register;
import Users.Teacher;
import Users.Student;

public class TeacherView {
    static Scanner in = new Scanner(System.in); 

    public static void welcome(Teacher loggedInTeacher) {
        System.out.println("Welcome to WSP, " + loggedInTeacher.getUsername() + "!");
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. See all news");
        System.out.println("2. View students in my courses");
        int option = in.nextInt();
        in.nextLine();  

        if (option == 0) {
            System.out.println("Bye bye!"); 
            MainView.welcome();
        }
        else if (option == 1) {
            ManagerView.seeNews();
        } 
        else if (option == 2) {
            viewStudentsInMyCourses(loggedInTeacher);  
        } 
        else {
            System.out.println("Invalid option, try again.");
            welcome(loggedInTeacher);  
        }
    }

    public static void viewStudentsInMyCourses(Teacher teacher) {
        boolean foundCourse = false;

        for (Course course : UserController.getAllCourses()) {
            if (course.getCourseTeacher().equals(teacher.getUsername())) {
                foundCourse = true;
                System.out.println("course: " + course.getCourseName());

                boolean courseHasStudents = false;

                for (Register register : DBContext.registerList) {
                    if (register.getCourse().getCourseId().equals(course.getCourseId())) {
                        Student student = register.getStudent();
                        System.out.println("student: " + student.getUsername());
                        
                        courseHasStudents = true;
                    }
                }

                if (!courseHasStudents) {
                    System.out.println("No has registered ");
                }

                System.out.println("------------------------");
            }
        }

        if (!foundCourse) {
            System.out.println("you don't have a course");
        }

        welcome(teacher);  
    }
}
