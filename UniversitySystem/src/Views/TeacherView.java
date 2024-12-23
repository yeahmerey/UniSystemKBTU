package Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import Controllers.UserController;
import DLL.DBContext;
import Enumerations.UrgencyLevel;
import Exception.UserNotFoundException;
import Utils.Attestation;
import Utils.Complaint;
import Utils.Course;
import Utils.News;
import Utils.Register;
import Users.Teacher;
import Users.Student;
import Users.Employee;
import Users.Manager; 
public class TeacherView {
    static Scanner in = new Scanner(System.in); 

    public static void welcome(Teacher loggedInTeacher) throws UserNotFoundException {
        System.out.println("Welcome to WSP, " + loggedInTeacher.getUsername() + "!");
        System.out.println("Please select an option:");
        System.out.println("- 0. Exit");
        System.out.println("- 1. See all news");
        System.out.println("- 2. View students in my courses");
        System.out.println("- 3. Is salary gived");
        System.out.println("- 4. Submit complaint");
        System.out.println("- 5. Put Marks");
        System.out.println("- 6. View Student Attestation"); 
        System.out.println("- 7. Open attendence");

        System.out.print("Option : ");
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
            viewStudentsInMyCourses(loggedInTeacher);  
        } 
        else if (option == 3) {
        	isGiveSalary(loggedInTeacher); 
        } 
        else if (option == 4) {
        	submitComplaint(loggedInTeacher); 
        }
        else if(option == 5) {
        	setStudentMarks(loggedInTeacher); 
        }
        else if(option == 6) {
        	viewStudentAttestation(loggedInTeacher); 
        }
        else if(option == 7) {
        	openAttendence(loggedInTeacher); 

        }
        else {
            System.out.println("Invalid option, try again.");
            welcome(loggedInTeacher);  
        }
    }
    public static void openAttendence(Teacher teacher) throws UserNotFoundException {
    	System.out.println("Choose course:");
    	List<Course> courses = DBContext.getCourse();
    	int  i = 1;
    	for(Course couse : courses) {
    		System.out.println(i + ". " + couse.getCourseName());
    		i++;
    	}
    	System.out.println("Enter course number:");
    	int courseChoise = in.nextInt();
    	in.nextLine();
    	
    	if (courseChoise < 1 ) {
            System.out.println("Invalid choice. Returning to main menu.");
            welcome(teacher);
            return;
        }
    	List<Student> students = DBContext.getStudents();
    	Course selectedCourses = courses.get(courseChoise - 1);
    	
        for (Student student : students) {
            for (Register register : DBContext.registerList) {
                if (register.getCourse().getCourseId().equals(selectedCourses.getCourseId()) &&
                        register.getStudent().getUsername().equals(student.getUsername())) {
                	
                	student.setattendent();
                    
                }
            }
        }

    	
    	
    	welcome(teacher);
    }

    public static void viewStudentAttestation(Teacher teacher) throws UserNotFoundException {
        System.out.println("Select a course:");
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : UserController.getAllCourses()) {
            if (course.getCourseTeacher().equals(teacher.getUsername())) {
                teacherCourses.add(course);
            }
        }

        if (teacherCourses.isEmpty()) {
            System.out.println("You have no courses.");
            welcome(teacher);
            return;
        }

        int i = 1;
        for (Course course : teacherCourses) {
            System.out.println(i + ". " + course.getCourseName());
            i++;
        }
        System.out.print("Enter the course number: ");
        int courseChoice = in.nextInt();
        in.nextLine();

        if (courseChoice < 1 || courseChoice > teacherCourses.size()) {
            System.out.println("Invalid course choice, try again!");
            welcome(teacher);
            return;
        }
        Course selectedCourse = teacherCourses.get(courseChoice - 1);

        System.out.println("Select a student:");
        List<Student> courseStudents = new ArrayList<>();
        for (Register register : DBContext.registerList) {
            if (register.getCourse().getCourseId().equals(selectedCourse.getCourseId())) {
                courseStudents.add(register.getStudent());
            }
        }

        if (courseStudents.isEmpty()) {
            System.out.println("No students for this course.");
            welcome(teacher);
            return;
        }

        i = 1;
        for (Student s : courseStudents) {
            System.out.println(i + ". " + s.getUsername());
            i++;
        }
        System.out.print("Enter the student number: ");
        int studentChoice = in.nextInt();
        in.nextLine();

        if (studentChoice < 1 || studentChoice > courseStudents.size()) {
            System.out.println("Invalid student choice.");
            welcome(teacher);
            return;
        }
        Student selectedStudent = courseStudents.get(studentChoice - 1);

        Attestation attestation = DBContext.getOrCreateAttestation(selectedCourse.getCourseId(), selectedStudent.getUsername());
        System.out.println("Student Attestation:");
        System.out.println(attestation);

        welcome(teacher);
    }

	public static void setStudentMarks(Teacher teacher) throws UserNotFoundException {
    	System.out.println("Select a course : "); 
    	List<Course> teacherCourses = new ArrayList<>() ;
    	for(Course course : UserController.getAllCourses()) {
    		if(course.getCourseTeacher().equals(teacher.getUsername())) {
    			teacherCourses.add(course); 
    		}
    	}
    	if(teacherCourses.isEmpty()) {
    		System.out.println("You have no courses") ;
    		welcome(teacher); 
    		return ; 
    	} 
    	int i = 1 ; 
    	for(Course course : teacherCourses) {
    		System.out.println(i + "." + course.getCourseName()); 
    		i ++ ; 
    	}
    	System.out.println("Enter the course number : ") ;
    	int courseChoice = in.nextInt() ;
    	in.nextLine() ; 
    	
    	if(courseChoice < 1 || courseChoice > teacherCourses.size()) {
    		System.out.println("Invalid course choice , try again!"); 
    		welcome(teacher) ;
    		return ; 
    	}
    	Course selectedCourse = teacherCourses.get(courseChoice - 1) ;
    	System.out.println("Select a student : ");
    	List<Student> courseStudents = new ArrayList<>(); 
    	for(Register register : DBContext.registerList) {
    		if(register.getCourse().getCourseId().equals(selectedCourse.getCourseId())) {
    			courseStudents.add(register.getStudent());     		}
    	}
    	if(courseStudents.isEmpty()) {
    		System.out.println("No students for this course.") ;
    		welcome(teacher) ;
    		return; 
    	}
    	i = 1 ;
    	for(Student s : courseStudents) {
    		System.out.println(i + ". " + s.getUsername()); 
    		i ++ ; 
    	}
    	System.out.println("Enter the student number : ") ;
    	int studentChoice = in.nextInt(); 
    	in.nextLine(); 
    	if (studentChoice < 1 || studentChoice > courseStudents.size()) {
            System.out.println("Invalid student choice.");
            welcome(teacher);
            return;
        }
    	Student selectedStudent = courseStudents.get(studentChoice - 1);

        // Установка оценок
        Attestation attestation = DBContext.getOrCreateAttestation(selectedCourse.getCourseId(), selectedStudent.getUsername());

        System.out.print("Enter score for the First Attestation: ");
        double firstAttScore = in.nextDouble();
        attestation.putFirstAttestation(firstAttScore);

        System.out.print("Enter score for the Second Attestation: ");
        double secondAttScore = in.nextDouble();
        attestation.putSecondAttestation(secondAttScore);

        System.out.print("Enter score for the Final Exam: ");
        double finalExamScore = in.nextDouble();
        attestation.putFinalExamScore(finalExamScore);

        System.out.println("Scores updated successfully:");
        System.out.println(attestation);
        DBContext.saveAttestations(); 
        welcome(teacher);
    	
    }
    public static void seeNews() {
        System.out.println("Loading all news...");

        for (News news : UserController.getAllNews()) {
            System.out.println(news);
            System.out.println("------------------------");
        }
    }
    public static void submitComplaint(Teacher teacher) throws UserNotFoundException {

        // Список студентов, на которых можно подать жалобу
        System.out.println("Select the student to complain about:");
        List<Student> students = DBContext.getStudents();
        int i = 1;
        for (Student student : students) {
            System.out.println(i + ". " + student.getUsername());
            i++;
        }
        System.out.print("Enter the student number: ");
        int studentChoice = in.nextInt();
        in.nextLine();  // Очищаем буфер

        // Проверка, что выбранный студент существует
        if (studentChoice < 1 || studentChoice > students.size()) {
            System.out.println("Invalid student choice.");
            return;
        }

        // Получаем выбранного студента
        Student selectedStudent = students.get(studentChoice - 1);

        // Выбор уровня приоритета жалобы
        System.out.println("Select the priority of your complaint:");
        System.out.println("1. High");
        System.out.println("2. Middle");
        System.out.println("3. Low");
        int priority = in.nextInt();
        in.nextLine();  // Очищаем буфер

        UrgencyLevel priorityLevel = UrgencyLevel.LOW;  // По умолчанию LOW
        switch (priority) {
            case 1:
                priorityLevel = UrgencyLevel.HIGH;
                break;
            case 2:
                priorityLevel = UrgencyLevel.MIDDLE;
                break;
            case 3:
                priorityLevel = UrgencyLevel.LOW;
                break;
            default:
                System.out.println("Invalid priority level, setting to Low.");
                priorityLevel = UrgencyLevel.LOW;
        }

        // Создаем объект жалобы
        Complaint complaint = new Complaint(teacher, priorityLevel, selectedStudent);

        // Если приоритет высокий, добавляем в новости
        if (priorityLevel == UrgencyLevel.HIGH) {
            // Создаем описание для новости
            String newsDescription = "Student " + selectedStudent.getUsername() + " is being called to the dean's office. ";
            
            // Создаем новость с этим описанием
            News news = new News("High priority complaint from " + teacher.getUsername(), newsDescription);
            DBContext.addNews(news);
            
            System.out.println("Your high-priority complaint has been added to the news: " + newsDescription);
            welcome(teacher);  // Возвращаемся в главное меню
        } else {
            // Если приоритет не высокий, жалоба отправляется менеджеру на рассмотрение
            Vector<Manager> managers = DBContext.getManagers(); // Получаем список менеджеров
            if (managers != null && !managers.isEmpty()) {
                Manager manager = managers.get(0);  // Предположим, что у нас только один менеджер
                // Можно добавить дополнительные действия для отправки жалобы менеджеру
            }

            welcome(teacher);  // Возвращаемся в главное меню
        }
    }


    public static void viewStudentsInMyCourses(Teacher teacher) throws UserNotFoundException {
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
    public static void isGiveSalary(Teacher teacher) throws UserNotFoundException {
    	if(Employee.isSalaryPaid()) {
    		System.out.println("Gived salary.");
    		
    	}else {
    		System.out.println("Not gived salary.");
    	} 
    	welcome(teacher); 
    }
}
