package roles;
import java.util.ArrayList;

import courses.Course;

/**
 * An abstyract class representing a user.
 */
public abstract class User {
	
	/**
	 * ID of user.
	 */
    private String id;
    
    /**
     * Name of user.
     */
    private String name;
    
    /**
     * Username for user.
     */
    private String username;
    
    /**
     * Password for user.
     */
    private String password;
    
    /**
     * User's list of courses.
     */
    private ArrayList<Course> courseList;

    /**
     * Creates a user.
     * @param id of user
     * @param name of user
     * @param username for user
     * @param password for user
     */
    public User(String id, String name, String username, String password){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.courseList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    
    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    /**
     * Returns id and name for user.
     */
    @Override
    public String toString() {
        return id + " " + name;
    }

    /**
     * Shows all information for the user's list of courses.
     * @param courses the given courses list
     */
    public void viewUserCourses(ArrayList<Course> courses){
        System.out.println("------------The course list------------");
        for (Course course : courses){
            System.out.println(course.toString());
        }
    }
}
