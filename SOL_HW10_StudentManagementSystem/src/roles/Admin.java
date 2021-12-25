package roles;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import courses.Course;

/**
 * A class representing an administrator.
 */
public class Admin extends User {

    /**
     * Creates an admin.
     * @param id of admin
     * @param name of admin
     * @param username of admin
     * @param password of admin
     */
    public Admin(String id, String name, String username, String password) {
        super(id, name, username, password);
    }

    /**
     * Adds new course to the system.
     * @param courseInSystem, list of all courses in the system
     * @param profInSystem, list of all professors in the system
     * @param userNameSet, set of all usernames in the system
     * @param s scanner for user input
     */
    public void addNewCourse(List<Course> courseInSystem, List<Professor> profInSystem, Set<String> userNameSet, Scanner s){

        String tempID;
        String tempName;
        String startTime;
        String endTime;
        String tempDate;
        String capacity;
        String profID;
        s.useDelimiter("\n");
        // get course ID
        while(true){
            boolean findExist = false;
            System.out.println("Please enter the course ID, or type 'q' to end.");
            tempID = s.next();
            if(tempID.equals("q")) return;
            for(Course c: courseInSystem){
                if(c.getId().equals(tempID)) {
                    System.out.println("The course already exist");
                    findExist = true;
                    break;
                }
            }
            if(!findExist) break;
        }

        // get course name
        System.out.println("Please enter the course name, or type 'q' to end.");
        tempName = s.next();
        // System.out.println(tempName);
        if(tempName.equals("q")) return;

        // get course start time
        System.out.println("Please enter the course start time, or type 'q' to end. eg. '19:00'");
        startTime = s.next();
        if(startTime.equals("q")) return;

        //get course end time
        System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'");
        endTime = s.next();
        if(endTime.equals("q")) return;

        // get course date
        System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW'");
        tempDate = s.next();
        if(tempDate.equals("q")) return;

        boolean[] date = convertDate(tempDate);

        // get course capacity
        System.out.println("Please enter the course capacity, or type 'q' to end. eg. '72'");
        capacity = s.next();
        if(capacity.equals("q")) return;

        // get lecturer of the new course
        System.out.println("Please enter the course lecturer's id, or type 'q' to end. eg. '001'");
        profID = s.next();
        if(profID.equals("q")) return;

        // check if the prof already exist
        boolean find = false;
        Professor prof = null;
        for(Professor p: profInSystem){
            if(p.getId().equals(profID)){
                find = true;
                prof = p;
                break;
            }
        }
        if(!find){
            System.out.println("The professor isn't in the system, please add this professor first");
            prof = addNewProf(profInSystem, userNameSet, s);
        }
        if(prof == null){
            System.out.println("Add the professor to system unsuccessfully");
            System.out.println("Unable to add this new course");
        }else{
            Course newCourse = new Course(tempID, tempName, startTime, endTime, date, Integer.valueOf(capacity), prof);
            // check if the newly added course has time conflict with other course of the lecturer
            for(Course otherC: prof.getCourseList()){
                if(newCourse.check_conflict(otherC)){
                    System.out.println("The new added course has time conflict with course: " + otherC);
                    System.out.println("Unable to add new course: " + newCourse);
                    return;
                }
            }
            // add the new course to the system
            courseInSystem.add(newCourse);
            // add the username to the set
            System.out.println("Successfully added the course: " + newCourse);
        }
    }

    /**
     * Converts the given date like "MF" to a boolean array with a size of 5.
     * For the boolean array, true means this course will be on the ith day of the week
     * For example, "MWF" -> {true, false, true, false, true}
     * @return the boolean array
     */
    public static boolean[] convertDate(String str){
        boolean[] date = new boolean[5];
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i)=='M'){
                date[0] = true;
            }else if(str.charAt(i)=='T'){
                date[1] = true;
            }else if(str.charAt(i)=='W'){
                date[2] = true;
            }else if(str.charAt(i)=='R'){
                date[3] = true;
            }else if(str.charAt(i)=='F'){
                date[4] = true;
            }
        }
        return date;
    }

    /**
     * Deletes a course from the given course list.
     * @param courseInSystem the list of all courses in the system
     * @param s scanner for user input
     */
    public void deleteCourse(List<Course> courseInSystem, Scanner s){
        boolean success = false;
        System.out.println("please enter the course ID, or type 'q' to end. eg. 'CIT591'");
        String tempID = s.next();
        if(tempID.equals("q")) return;
        
        Iterator<Course> courseIterator = courseInSystem.iterator();
        Course c;
        while(courseIterator.hasNext()) {
        	c = courseIterator.next();
        	if(c.getId().equals(tempID)){
        		courseIterator.remove();
                success = true;
                break;
            }
        }
        if(success) System.out.println("successfully deleted the course");
        else System.out.println("Cannot find this course");
    }

    /**
     * Adds a new student to the list of all students in the system.
     * @param studentsInSystem list of all students in the system
     * @param courseInSystem list of all courses in the system
     * @param userNameSet set of all usernames in the system
     * @param s scanner for user input
     */
    public void addNewStudent(List<Student> studentsInSystem, List<Course> courseInSystem, Set<String> userNameSet, Scanner s){
        //String id, String name, String username, String password, HashMap<Course, String> grades
        String tempID;
        String tempName;
        String tempUsername;
        String tempPassword;
        HashMap<Course, String> grades = new HashMap<>();
        boolean end = false;

        // get student's ID
        while (true){
            System.out.println("Please enter the student's ID, or type 'q' to quit");
            tempID = s.next();
            if(tempID.equals("q")) {
                end = true;
                break;
            }
            // check if the id already exist
            boolean findExist = false;
            for(Student stu: studentsInSystem){
                if(stu.getId().equals(tempID)){
                    System.out.println("The student ID already exists");
                    findExist = true;
                    break;
                }
            }
            if(!findExist) break;
        }
        if(end) return;

        // get student's name
        System.out.println("Please enter student's name, or type'q' to end");
        tempName = s.next();
        if(tempName.equals("q")) return;

        // get student's username
        while(true){
            System.out.println("Please enter a username");
            tempUsername = s.next();
            if(tempUsername.equals("q")) return;
            if(userNameSet.contains(tempUsername)){
                System.out.println("The username you entered is not available.");
            }else{
                break;
            }
        }

        // get the password
        System.out.println("Please enter a password");
        tempPassword = s.next();
        if(tempPassword.equals("q")) return;

        // get the grades and course information the students already taken
        while(true){
            String grade = "";
            Course course = null;
            System.out.println("Please enter ID of a course which this student already took, one in a time");
            System.out.println("Type 'q' to quit, type 'n' to stop adding.");
            String takenCourseID = s.next();

            if(takenCourseID.equals("q")) return;
            if(takenCourseID.equals("n")) break;

            // check if the course already exist
            for(Course c: courseInSystem){
                if(c.getId().equals(takenCourseID)){
                    System.out.println("Please enter the grade, eg, 'A'");
                    grade = s.next();
                    course = c;
                }
            }
            if(course == null){
                System.out.println("The course you entered does not exists, please add the course first");
                continue;
            }
            grades.put(course, grade);

        }

        // add the course
        studentsInSystem.add(new Student(tempID, tempName, tempUsername, tempPassword, grades));
        // add the username to the set
        userNameSet.add(tempUsername);
        System.out.println("Successfully added the new student: " + tempID  + " " + tempName);
    }

    /**
     * Deletes a student from the list of all students in the system.
     * @param stuInSystem a list from which a student's info is going to be deleted
     * @param s scanner
     */
    public void deleteStudent(List<Student> stuInSystem, Set<String> userNameSet, Scanner s){
        boolean success = false;
        System.out.println("Please enter student's id, or type 'q' to end");

        String tempID = s.next();
        if(tempID.equals("q")) return;
        
        Iterator<Student> studentIterator = stuInSystem.iterator();
        Student student;
        while(studentIterator.hasNext()) {
        	student = studentIterator.next();
        	if(student.getId().equals(tempID)){

        	    // remove this student from the courses they are taking
                for (Course c: student.getCourseList()) {
                    c.removeStudent(student);
                }
                
        		studentIterator.remove();
        		userNameSet.remove(student.getUsername());
                success = true;
                break;
            }
        }
        if(success) System.out.println("Successfully deleted the student");
        else System.out.println("Cannot find this student");
    }

    /**
     * Adds a new professor to the list of all professors.
     * @param profInSystem the list of all professors in the system
     * @param userNameSet a set with all usernames in the system
     * @param s scanner for user input
     * @return the newly added professor
     */
    public Professor addNewProf(List<Professor> profInSystem, Set<String> userNameSet, Scanner s){
        //String id, String name, String username, String password
        String tempID;
        String tempName;
        String tempUsername;
        String tempPassword;
        boolean end = false;

        // get the ID
        while (true){
            boolean findExist = false;
            System.out.println("Please enter the professor's ID, or type 'q' to quit");
            tempID = s.next();
            if(tempID.equals("q")) {
                end = true;
                break;
            }
            // check if the ID already exist
            for(Professor prof: profInSystem){
                if(prof.getId().equals(tempID)){
                    System.out.println("The ID already exists");
                    findExist = true;
                    break;
                }
            }
            if(!findExist) break;
        }
        if(end) return null;

        // get the name
        System.out.println("Please enter professor's name, or type 'q' to end");
        tempName = s.next();
        if(tempName.equals("q")) return null;

        // get the username
        while(true){
            System.out.println("Please enter a username");
            tempUsername = s.next();
            if(tempUsername.equals("q")) return null;
            if(userNameSet.contains(tempUsername)){
                System.out.println("The username you entered is not available.");
            }else{
                break;
            }
        }

        //get the password
        System.out.println("Please enter a password");
        tempPassword = s.next();
        if(tempPassword.equals("q")) return null;
        Professor res = new Professor(tempID, tempName, tempUsername, tempPassword);

        // add the newly created professor to the list
        profInSystem.add(res);
        // add the username to the set
        userNameSet.add(tempUsername);
        System.out.println("Successfully added the new professor: " + tempID  + " " + tempName);
        return res;
    }

    /**
     * Deletes a professor from the list of all professors.
     * @param profInSystem the list of all professors in the system
     * @param s scanner for user input
     */
    public void deleteProf(List<Professor> profInSystem, Set<String> userNameSet, Scanner s){
        boolean success = false;
        System.out.println("Please enter the id, or type 'q' to end");

        String tempID = s.next();
        String profName = "";
        if(tempID.equals("q")) return;
        
        Iterator<Professor> professorIterator = profInSystem.iterator();
        Professor prof;
        while(professorIterator.hasNext()) {
        	prof = professorIterator.next();
        	if(prof.getId().equals(tempID)){
        		professorIterator.remove();
        		userNameSet.remove(prof.getUsername());
        		profName = prof.getName();
                success = true;
                break;
            }
        }
        
        if(success) System.out.println("Successfully deleted Professor " + tempID + " " + profName);
        else System.out.println("Cannot find this professor");
    }
}
