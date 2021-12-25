package roles;
import java.util.HashMap;

import courses.Course;

/**
 * A class representing a student.
 */
public class Student extends User {

	/**
	 * Map of grades for this student.
	 */
    private HashMap<Course, String> grades;

    /**
     * Creates a student.
     * @param id of student
     * @param name of student
     * @param username of student
     * @param password of student
     * @param grades for student
     */
    public Student(String id, String name, String username, String password, HashMap<Course, String> grades){
        super(id, name, username, password);
        this.grades = grades;
    }

    public HashMap<Course, String> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<Course, String> grades) {
        this.grades = grades;
    }

    /**
     * Adds the given course to this student's list of courses.
     * @param course to add
     * @return true if added successfully
     * If the given course has a time conflict with any other courses in the student's list, return false
     */
    public boolean addClass(Course course){
        for(Course other: getCourseList()){
            if(course.check_conflict(other)) {
                System.out.println("The course you selected has time conflict with " + other.getId() + " " + other.getCourseName() + ".");
                return false;
            }
        }
        // no time conflict, okay to add
        getCourseList().add(course);
        course.addStudent(this);
        return true;
    }

    /**
     * Drops the given course from student's list of courses.
     * @param course to drop
     * @return true if dropped successfully
     * If the given course is not in the student's list, return false
     */
    public boolean dropClass(Course course){

        if(!getCourseList().contains(course)){
            System.out.println("The course isn't in your schedule.");
            return false;
        }

        getCourseList().remove(course);
        course.removeStudent(this);
        return true;
    }

    /**
     * Views the student's grades for all his/her courses.
     */
    public void viewGrades(){
        System.out.println("Here are the courses you already taken, with your grade in a letter format");
        for(Course c: grades.keySet()){
            System.out.println("Grade of " + c.getId() + " " + c.getCourseName() + ": " + grades.get(c));
        }
    }
}
