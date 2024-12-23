package DLL;

import java.io.File;
import java.util.*; 
import Users.*;
import Utils.News;
import Utils.Attestation;
import Utils.Course;
import Utils.Register;
import Utils.Request;

public class DBContext {
    
    private static String fullPath = "/Users/bekzatshaiyrgozha/Desktop/UniSystemKBTU/UniversitySystem/src/Data/" ; 
    private static DBContext db = new DBContext();

    // Static lists holding the data
    public static Vector<Teacher> teacher;
    public static Vector<Student> student;
    public static Vector<Manager> manager;
    public static Vector<News> newsList;
    public static Vector<Course> courseList;
    public static Vector<Register> registerList;
    public static Vector<Attestation> attestationList ;
    public static Vector<Request> requests = new Vector<>();
    private static Vector<Student> studentsResearchers = new Vector<>();

    // Static initialization block
    static {
        teacher = getTeachers();
        student = getStudents();
        manager = getManagers();
        newsList = getNews();
        courseList = getCourse();
        registerList = getRegisters();
        attestationList = getAttestations() ;
    }

    private DBContext() {}

    private static Attestation getAttestation(String courseId , String studentUsername) {
    	for (Attestation attestation : attestationList) {
            if (attestation.getCourseId().equals(courseId) && attestation.getStudentUsername().equals(studentUsername)) {
                return attestation;
            }
        }
        return null;
	}
    public static Vector<Attestation> getAttestations(){
    	String filePath = fullPath + "attestations.txt" ; 
    	return getObjectsFromFile(filePath , Attestation.class); 
    }
    public static boolean saveAttestations() {
        return ReaderWriter.serialize(attestationList, fullPath + "attestations.txt");
    }

	// Methods to add items to the lists
    public static void addRequest(Request request) {
        requests.add(request);
    }
    
    public static Vector<Request> getRequests() {
        return requests;
    }

    public static Vector<Student> getStudentsResearchers() {
        return studentsResearchers;
    }

    // Method to get all students from the file
    public static Vector<Student> getStudents() {
        String filePath = fullPath + "students.txt";
        return getObjectsFromFile(filePath, Student.class);
    }

    // Method to get all teachers from the file
    public static Vector<Teacher> getTeachers() {
        String filePath = fullPath + "teachers.txt";
        return getObjectsFromFile(filePath, Teacher.class);
    }

    // Method to get all managers from the file
    public static Vector<Manager> getManagers() {
        String filePath = fullPath + "manager.txt";
        return getObjectsFromFile(filePath, Manager.class);
    }

    // Method to get news from the file
    public static Vector<News> getNews() {
        String filePath = fullPath + "news.txt";
        return getObjectsFromFile(filePath, News.class);
    }

    // Method to get courses from the file
    public static Vector<Course> getCourse() {
        String filePath = fullPath + "course.txt";
        return getObjectsFromFile(filePath, Course.class);
    }

    // Method to get registers from the file
    public static Vector<Register> getRegisters() {
        String filePath = fullPath + "register.txt";
        return getObjectsFromFile(filePath, Register.class);
    }

    // Helper method to read objects from file
    private static <T> Vector<T> getObjectsFromFile(String filePath, Class<T> type) {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new Vector<>();
        }
        Object o = ReaderWriter.deserialize(filePath);
        if (o instanceof Vector) {
            return (Vector<T>) o;
        }
        return new Vector<>();
    }

    // Methods for saving data back to files
    public static boolean saveTeachers() {
        return ReaderWriter.serialize(teacher, fullPath + "teachers.txt");
    }

    public static boolean saveStudents() {
        return ReaderWriter.serialize(student, fullPath + "students.txt");
    }

    public static boolean saveManagers() {
        return ReaderWriter.serialize(manager, fullPath + "manager.txt");
    }

    public static boolean saveRegisters() {
        return ReaderWriter.serialize(registerList, fullPath + "register.txt");
    }

    public static boolean saveNews() {
        return ReaderWriter.serialize(newsList, fullPath + "news.txt");
    }

    public static boolean saveCourse() {
        return ReaderWriter.serialize(courseList, fullPath + "course.txt");
    }

    // Methods for adding new objects to the lists
    public static void addStudentToResearchers(Student student) {
        studentsResearchers.add(student);
    }

    public static void addNews(News news) {
        newsList.add(news);
        saveNews();
    }

    public static void addCourse(Course course) {
        courseList.add(course);
        saveCourse();
    }

    public static void addRegister(Register register) {
        registerList.add(register);
        saveRegisters();
    }

    // Method to get all users (students, teachers, managers)
    public static Vector<User> getAllUsers() {
        Vector<User> allUsers = new Vector<>();
        allUsers.addAll(teacher);
        allUsers.addAll(student);
        allUsers.addAll(manager);
        return allUsers;
    }

    // Singleton getter
    public static DBContext getDb() {
        return db;
    }
    //method to create or get attestation : 
    public static Attestation getOrCreateAttestation(String courseId , String studentUsername) {
    	for (Attestation attestation : attestationList) {
            if (attestation.getCourseId().equals(courseId) && attestation.getStudentUsername().equals(studentUsername)) {
                return attestation;
            }
        }

        Attestation newAttestation = new Attestation(courseId, studentUsername);
        attestationList.add(newAttestation);
        return newAttestation;
    }
    // Method to count all employees (teachers, managers)
    public static int getCountOfEmployees() {
        return teacher.size() + manager.size();
    }
}
