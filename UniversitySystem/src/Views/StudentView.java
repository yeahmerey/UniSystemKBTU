package Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import Users.Student;
import Utils.Attestation;
import Utils.Course;
import Utils.News;
import Utils.Register;
import Utils.Request;
import Controllers.UserController;
import DLL.DBContext;
import Exception.UserNotFoundException;

public class StudentView {
    static Scanner in = new Scanner(System.in); 

    public static void welcome(Student loggedInStudent) throws UserNotFoundException {
        System.out.println("\nWelcome to WSP, " + loggedInStudent.getUsername() + "!");
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. See all news");
        System.out.println("2. Send request");
        System.out.println("3. View My Attestations"); 
        System.out.println("4. Attendent");
        System.out.println("5. Attendent or not");

        System.out.println("7. View My Profile");
        System.out.println("8. Register for a Course");
        
        if (statusResetTimer == null) {
            startAttendentStatusResetTimer(); 
        }
        
        int option = in.nextInt();
        in.nextLine();  

        if (option == 0) {
            System.out.println("Bye bye!");
            MainView.welcome();
        } 
        else if (option == 1) {
            seeNews();
        } 
        else if (option == 2) {
        	sendRequest(loggedInStudent); 
        }
        else if(option == 3) {
        	viewMyAttestations(loggedInStudent) ;
        }
        else if (option == 4) {
        	attendent(loggedInStudent); 
        }
        else if (option == 5) {
        	attendentOrNot(loggedInStudent); 
        }

        else if (option == 7) {
            myProfile(loggedInStudent);  
        }
        else if (option == 8) {
        	registerForCourse(loggedInStudent);
        }
        else {
            System.out.println("Invalid option. Please try again.");
            welcome(loggedInStudent);  
        }
    }
    
    public static void attendentOrNot(Student student) throws UserNotFoundException {
    	if(student.getAttendentStatus()) {
    		System.out.println("Yes, attendent.");
    	}else {
    		System.out.println("No, attendention.");
    	}
    	welcome(student);
    }
    public static void attendent(Student student) throws UserNotFoundException {
        if(student.getattendent() == true) {
            System.out.println("Are you want to attend? (yes or no)");
            String answer = in.nextLine();
            if(answer.equals("yes")) {
                student.setAttendentStatus(true);  
                System.out.println("You are now marked as attended.");
            } else {
                System.out.println("You did not attend.");
                student.setAttendentStatus(false);
            }
        } else {
            System.out.println("You have already marked as attended.");
        }
        welcome(student);
        
    }
    private static Timer statusResetTimer;

    private static void startAttendentStatusResetTimer() {
        if (statusResetTimer != null) {
            statusResetTimer.cancel(); 
        }

        statusResetTimer = new Timer(); 
        statusResetTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Student st : DBContext.getStudents()) {
                    st.resetAttendentStatus(); 
                }
                System.out.println("All students' attendent statuses have been reset.");
            }
        }, 180 * 1000, 180 * 1000); 
    }

    private static void viewMyAttestations(Student loggedInStudent) throws UserNotFoundException {
    	System.out.println("Select a course to view your attestation:");
        
        List<Course> studentCourses = new ArrayList<>();
        for (Register register : DBContext.registerList) {
            if (register.getStudent().getUsername().equals(loggedInStudent.getUsername())) {
                studentCourses.add(register.getCourse());
            }
        }

        if (studentCourses.isEmpty()) {
            System.out.println("You are not registered for any courses.");
            welcome(loggedInStudent);
            return;
        }

        int i = 1;
        for (Course course : studentCourses) {
            System.out.println(i + ". " + course.getCourseName());
            i++;
        }
        System.out.print("Enter the course number: ");
        int courseChoice = in.nextInt();
        in.nextLine();

        if (courseChoice < 1 || courseChoice > studentCourses.size()) {
            System.out.println("Invalid course choice, try again!");
            welcome(loggedInStudent);
            return;
        }

        Course selectedCourse = studentCourses.get(courseChoice - 1);

        
        Attestation attestation = DBContext.getOrCreateAttestation(selectedCourse.getCourseId(), loggedInStudent.getUsername());
        System.out.println("Your Attestation for Course: " + selectedCourse.getCourseName());
        System.out.println(attestation);

        welcome(loggedInStudent);
	}
	public static void seeNews() {
        System.out.println("Loading all news...");

        for (News news : UserController.getAllNews()) {
            System.out.println(news);
            System.out.println("------------------------");
        }
    }
    public static void sendRequest(Student student) throws UserNotFoundException {
    	System.out.println("Enter your request description:");
        String description = in.nextLine();

        Request request = new Request(student, description);
        DBContext.addRequest(request);

        System.out.println("Your request has been sent!");
        welcome(student);
    }
    public static void myProfile(Student student) throws UserNotFoundException {
        System.out.println("\n--- Student Profile ---");
        System.out.println("Username: " + student.getUsername());
        System.out.println("Faculty: " + student.getFaculty());
        System.out.println("Semester: " + student.getStudyYear());
        System.out.println("-----------------------");
        welcome(student);

    }
    public static void registerForCourse(Student student) throws UserNotFoundException {
        // Получаем все курсы
        Vector<Course> availableCourses = UserController.getAllCourses();

        // Фильтруем курсы по факультету и учебному году
        Vector<Course> filteredCourses = new Vector<>();
        for (Course course : availableCourses) {
            if (course.getFaculty().equals(student.getFaculty()) && course.getStudyYear() == student.getStudyYear()) {
                filteredCourses.add(course);
            }
        }

        // Если нет доступных курсов, выводим сообщение и возвращаемся в главное меню
        if (filteredCourses.isEmpty()) {
            System.out.println("Нет доступных курсов для вашего факультета и учебного года.");
            welcome(student);
            return;
        }

        // Выводим список доступных курсов с номерами
        System.out.println("\nДоступные курсы для вас:");
        for (int i = 0; i < filteredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + filteredCourses.get(i).getCourseName());
        }

        // Запрашиваем номер курса для регистрации
        System.out.print("Введите номер курса для регистрации: ");
        int courseChoice = in.nextInt();
        in.nextLine(); // Очищаем буфер

        // Проверяем, что введен правильный номер курса
        if (courseChoice < 1 || courseChoice > filteredCourses.size()) {
            System.out.println("Неверный номер курса. Пожалуйста, попробуйте снова.");
            return;
        }

        // Получаем выбранный курс
        Course selectedCourse = filteredCourses.get(courseChoice - 1);

       // Проверяем, зарегистрирован ли студент на курс
     
            // Если студент еще не зарегистрирован, создаем новую регистрацию
            Register newRegister = new Register(student, selectedCourse);
            DBContext.addRegister(newRegister); // Добавляем регистрацию в базу данных
            System.out.println("Вы успешно зарегистрировались на курс: " + selectedCourse.getCourseName());

        // Возвращаемся в главное меню после регистрации
        welcome(student);
    }

}
    
