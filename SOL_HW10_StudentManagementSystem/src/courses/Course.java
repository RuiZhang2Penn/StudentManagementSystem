package courses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import roles.Professor;
import roles.Student;

/**
 * A class to represent a course.
 */
public class Course {
	
	/**
	 * Course id.
	 */
    private String id;
    
    /**
     * Course name.
     */
    private String courseName;
    
    /**
     * Course start time.
     * format: 13:00 (in 24 hours)
     */
    private String startTime;
    
    /**
     * Course end time.
     * format: 15:00 (in 24 hours)
     */
    private String endTime;
    
    /**
     * Array representing days of the week for the course.
     * Each day represented by a true value in array.
     */
    boolean[] date;
    
    /**
     * List of students enrolled in course.
     */
    private List<Student> stuList;
    
    /**
     * Course capacity.
     */
    private int courseCapacity;
    
    /**
     * Number of enrolled students.
     */
    private int stuNum;
    
    /**
     * Course faculty.
     */
    private Professor lecturer;

    /**
     * Creates a new course.
     * @param id for course
     * @param name of course
     * @param start of course
     * @param end of course
     * @param date days of course
     * @param capacity of course
     * @param prof for course
     */
    public Course(String id, String name, String start, String end, boolean[] date, int capacity, Professor prof){
        this.id = id;
        this.courseName = name;
        this.startTime = start;
        this.endTime = end;
        this.date = date;
        this.courseCapacity = capacity;
        this.lecturer = prof;
        
        this.stuNum = 0;
        this.stuList = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public List<Student> getStuList() {
        return stuList;
    }
    
    public void setStuList(List<Student> stuList) {
        this.stuList = stuList;
    }
    
    public int getCourseCapacity() {
        return courseCapacity;
    }
    
    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }
    
    public Professor getLecturer() {
        return lecturer;
    }
    
    public void setLecturer(Professor lecturer) {
        this.lecturer = lecturer;
    }
    
    public boolean[] getDate() {
        return date;
    }
    
    public void setDate(boolean[] date) {
        this.date = date;
    }
    
    public int getStuNum() {
        return stuNum;
    }
    
    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    /**
     * Checks if this course is available at this time.
     * If the number of students who are enrolled in the course > the capacity of the course
     * @return false, otherwise, return true
     */
    public boolean check_availability(){
        return getStuNum() < getCourseCapacity();
    }

    /**
     * Helper function to convert given time to decimal format.
     * For example, 13:30 -> 13.5
     * @param time in the format "14:00"
     * @return double format
     */
    private double convertTime(String time){
        String[] times = time.split(":");
        return Integer.valueOf(times[0]) + Integer.valueOf(times[1]) / 60.0;
    }

    /**
     * Checks if this course has a time conflict with another given course.
     * @param other course to check
     * @return true if both courses have time conflict, otherwise false
     */
    public boolean check_conflict(Course other){

        for(int i = 0; i < 5; i++){
            if(date[i] && other.getDate()[i]){
                // check whether the same time in this day
                if(this.convertTime(endTime) <= other.convertTime(other.startTime) || other.convertTime(other.endTime) <= this.convertTime(startTime)) continue;
                else return true;
            }
        }
        return false;
    }

    /**
     * Adds the given student to the course.
     * @param s, the student to be added to the course
     * @return true if added successfully
     */
    public boolean addStudent(Student s){
        stuNum++;
        stuList.add(s);
        return true;
    }

    /**
     * Removes the given student from the course.
     * @param s, the student to be removed from the course
     * @return true, if removed successfully
     */
    public boolean removeStudent(Student s){
        stuNum--;
        stuList.remove(s);
        return true;
    }

    /**
     * Shows all students who are enrolled in this course.
     */
    public void showStudentList(){
        for(Student s: getStuList()){
            System.out.println(s.toString());
        }
    }

    /**
     * Converts the dates of this course like {true, false, true, false, true} to a string format "MWF".
     * For the boolean array, true means this course will be on the ith day of the week.
     * @return the date in the string format like "MF"
     */
    private String convertDateToString(){
        StringBuilder sb = new StringBuilder("MTWRF");
        int index = 0;
        for(int i = 0; i < 5; i++){
            if(!date[i]) sb.deleteCharAt(index);
            else index++;
        }
        return sb.toString();
    }

    /**
     * Represents course as string with important information.
     */
    @Override
    public String toString() {
        return id + '|' + courseName + ", " + startTime + '-' + endTime + ' ' +
                "on " + convertDateToString() +
                ", with course capacity: " + courseCapacity +
                ", students: " + stuNum +
                ", lecturer: Professor " + lecturer.getName();
    }
}
