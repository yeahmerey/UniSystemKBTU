package Utils;

import java.io.Serializable;
import Enumerations.Faculty;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseId;      
    private String courseName;   
    private String courseTeacher;
    private int studyYear; 
    private Faculty faculty;
    public Course(String courseId, String courseName, String courseTeacher , int studyYear, Faculty faculty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTeacher = courseTeacher;
        this.studyYear = studyYear;
        this.faculty = faculty;

    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
    public int getStudyYear() {
        return studyYear;
    }

    public void setstudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    @Override
    public String toString() {
        return "- Course code : " + courseId + "\n- Course Name : " + courseName + "\n- Course Teacher : " + courseTeacher + "\n- Faculty and study year : " + faculty + " , " + studyYear ; 
    }
}
