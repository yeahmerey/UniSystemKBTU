package Controllers;
import java.util.*;

import DLL.DBContext;
import Enumerations.UserType;
import Users.*;
import Utils.Course;
import Utils.News;
import Utils.Register; 
import Exception.UserNotFoundException;
public class UserController {
	public static boolean authorize(String fileName , String username , String password)throws UserNotFoundException {
		Vector<?> users = null ; 
		if (fileName.equals("teacher.txt")) {
            users = (Vector<Teacher>) UserController.getUsers(UserType.TEACHER);
        } else if (fileName.equals("students.txt")) {
            users = (Vector<Student>) UserController.getUsers(UserType.STUDENT);
        } else if (fileName.equals("managers.txt")) {
            users = (Vector<Manager>) UserController.getUsers(UserType.MANAGER);
        }
        if (users == null || users.isEmpty()) {
        	throw new UserNotFoundException("User type " + fileName + " not found."); 
        }

        for (Object user : users) {
            if (user instanceof User) {
                User u = (User) user;
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    return true;
                }
            }
        }

        return false;
	}
	public static Student getStudentByUsername(String username) {
	    Vector<Student> students = DBContext.getStudents();
	    for (Student student : students) {
	        if (student.getUsername().equals(username)) {
	            return student;  
	        }
	    }
	    
	    return null; 
	}
	public static Teacher getTeacherByUsername(String username) {
	    Vector<Teacher> teachers = DBContext.getTeachers();
	    for (Teacher teacher : teachers) {
	        if (teacher.getUsername().equals(username)) {
	            return teacher;
	        }
	    }
	    return null;
	}

	
	public static Vector<Register> getAllRegistrations() {
        return DBContext.registerList;
    }
	
    public static void addRegistration(Student student, Course course) {
        Register register = new Register(student, course);
        DBContext.addRegister(register);  
    }

	public static Object getUsers(UserType type) {
		switch (type){
		case TEACHER:
			return DBContext.getTeachers();
		case STUDENT:
			return DBContext.getStudents();
		case MANAGER:
			return DBContext.getManagers();
		default:
			return null;

	}
	}
	public static Vector<User> getAllUsers(){
		Vector<User> allUsers = new Vector<>();
        allUsers.addAll(DBContext.getTeachers());  
        allUsers.addAll(DBContext.getStudents());  
        allUsers.addAll(DBContext.getManagers());  
        return allUsers;
	}
	public static Vector<News> getAllNews(){
		return DBContext.getDb().getNews(); 
	}
	public static Vector<Course> getAllCourses(){
		return DBContext.getDb().getCourse();
	}
}