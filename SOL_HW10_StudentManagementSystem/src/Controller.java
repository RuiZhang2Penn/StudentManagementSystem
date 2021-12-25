import java.util.*;

import courses.Course;
import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

/**
 * Controller for the student management system.
 */
public class Controller {
	
	/**
	 * Logged in student.
	 */
    Student student;
    
    /**
     * Logged in professor.
     */
    Professor prof;
    
    /**
     * Logged in admin.
     */
    Admin admin;
    
    /**
     * List of all courses in the system.
     */
    List<Course> courseList;
    
    /**
     * List of all professors in the system.
     */
    List<Professor> profList;
    
    /**
     * List of all admins in the system.
     */
    List<Admin> adminList;
    
    /**
     * List of all students in the system.
     */
    List<Student> studentList;
    
    /**
     * Set of all usernames in the system.
     */
    Set<String> userNameSet =  new HashSet<>();

    /**
     * Gets list of all professors, list of all courses, list of all admins, and list of all students. 
     */
    public Controller() {
        profList = getProList();
        courseList = getCourseList();
        adminList = getAdminList();
        studentList = getStuList();
    }

    /**
     * Gets list of courses from the file.
     * @return list of all courses
     */
    public List<Course> getCourseList() {
        // add prof's course list
        // add lecturer
        List<Course> list = new ArrayList<>();
        ArrayList<String[]> database = FileInfoReader.readInfo("courseInfo.txt");
        for(String[] data: database){
            //String id, String name, String start, String end, boolean[] date, int capacity, Professor prof
            //this.stuNum = 0;
            //this.stuList = new ArrayList<>();
            //CIT 590; Programming Languages and Techniques; Brandon L Krakowsky; MW; 16:30; 18:00; 110
            boolean[] date = Admin.convertDate(data[3]);
            Professor lecturer = null;
            for(Professor p: profList){
                if(data[2].trim().equals(p.getName())) {
                    lecturer = p;
                    break;
                }
            }
            if(lecturer == null)
                System.out.println(data[2]);
            Course newCourse = new Course(data[0], data[1], data[4], data[5], date, Integer.valueOf(data[6].trim()), lecturer);
            lecturer.getCourseList().add(newCourse);
            list.add(newCourse);
        }

        return list;
    }
    
    /**
     * Gets list of professors from the file.
     * @return list of all professors
     */
    public List<Professor> getProList() {
        List<Professor> list = new ArrayList<>();
        ArrayList<String[]> database = FileInfoReader.readInfo("profInfo.txt");
        for(String[] data: database){
            //String id, String name, String username, String password
            //Clayton Greenberg; 001; Greenberg; password590
            new Professor(data[1], data[0], data[2], data[3]);
            list.add(new Professor(data[1], data[0], data[2], data[3]));
            // new prof.courselist updated in getCourseList()
            userNameSet.add(data[2]);
        }
        return list;
    }

    /**
     * Gets list of admins from the file.
     * @return list of all admins
     */
    public List<Admin> getAdminList() {
        List<Admin> list = new ArrayList<>();
        ArrayList<String[]> database = FileInfoReader.readInfo("adminInfo.txt");
        for(String[] data: database){
            //String id, String name, String username, String password
            //001; admin; admin01; password590
            list.add(new Admin(data[0], data[1], data[2], data[3]));
            userNameSet.add(data[2]);
        }
        return list;
    }

    /**
     * Gets list of students from the file.
     * @return list of all students
     */
    public List<Student> getStuList() {
        List<Student> list = new ArrayList<>();
        ArrayList<String[]> database = FileInfoReader.readInfo("studentInfo.txt");
        for(String[] data: database){
            //String id, String name, String username, String password, HashMap<Course, String> grades
            //courseList
            //001; StudentName1; testStudent; password590; CIS 191: A, CIS 320: A
            HashMap<Course, String> grades = new HashMap<>();
            String[] courseWithGrade = data[4].split(",");
            for(String str: courseWithGrade){
                String[] temp = str.trim().split(":");
                Course c = queryCourse(temp[0].trim());
                grades.put(c, temp[1].trim());
            }
            list.add(new Student(data[0], data[1], data[2], data[3], grades));
            userNameSet.add(data[2]);
        }
        return list;
    }

    /**
     * Shows the main menu.
     */
    public void mainMenu(){
        System.out.println("--------------------------");
        System.out.println("Students Management System");
        System.out.println("--------------------------");
        System.out.println(" 1 -- Login as a student");
        System.out.println(" 2 -- Login as a professor");
        System.out.println(" 3 -- Login as an admin");
        System.out.println(" 4 -- Quit the system");
        System.out.println();
        System.out.println("Please enter your option, eg. '1'. ");
    }

    /**
     * Shows the student menu.
     */
    public void stuMenu(){
        System.out.println("-------------------------------");
        System.out.println("      Welcome, " + student.getName());
        System.out.println("-------------------------------");
        System.out.println(" 1 -- View all courses");
        System.out.println(" 2 -- Add courses to your list");
        System.out.println(" 3 -- View selected courses");
        System.out.println(" 4 -- Drop courses in your list");
        System.out.println(" 5 -- View grades");
        System.out.println(" 6 -- Return to previous menu");
        System.out.println();
        System.out.println("Please enter your option, eg. '1'. ");

    }

    /**
     * Shows the professor menu.
     */
    public void profMenu(){
        System.out.println("------------------------------------");
        System.out.println("      Welcome, " + prof.getName());
        System.out.println("------------------------------------");
        System.out.println("1 -- View given courses");
        System.out.println("2 -- View student list of the given course");
        System.out.println("3 -- Return to the previous menu");
        System.out.println();
        System.out.println("Please enter your option, eg. '1'. ");
    }

    /**
     * Shows the admin menu.
     */
    public void adminMenu(){
        System.out.println("-------------------------------");
        System.out.println("      Welcome, " + admin.getName());
        System.out.println("-------------------------------");
        System.out.println("1 -- View all courses");
        System.out.println("2 -- Add new courses");
        System.out.println("3 -- Delete courses");
        System.out.println("4 -- Add new professor");
        System.out.println("5 -- Delete professor");
        System.out.println("6 -- Add new student");
        System.out.println("7 -- Delete student");
        System.out.println("8 -- Return to previous menu");
        System.out.println();
        System.out.println("Please enter your option, eg. '1'. ");
    }

    /**
     * Logs in a student/professor/admin or quits the system.
     * @param s scanner for user input
     * @param option string to represent the user's choose
     */
    public void login(Scanner s, String option){
        // quit
        if(option.equals("4")){
            System.exit(0);
        }else if(!option.equals("1") && !option.equals("2") && !option.equals("3")){
            mainMenu();
            option = s.next();
            login(s, option);
            return;
        }

        String username;
        String password;
        System.out.println("Please enter your username, or type 'q' to quit.");
        username = s.next();
        if(username.equals("q")) return;
        System.out.println("Please enter your password, or type 'q' to quit.");
        password = s.next();
        if(password.equals("q")) return;

        this.student = null;
        this.prof = null;
        this.admin = null;

        // student login
        if(option.equals("1")){
            for(Student stu: studentList){
                if(stu.getUsername().equals(username) && stu.getPassword().equals(password)) {
                    student = stu;
                    break;
                }
            }
            // find the student in database
            if(student != null){
                stuOperate(s);
            }else{
                // ask for username and password again
                System.out.println("The username/password is not correct");
                login(s, option);
            }
        }else if(option.equals("2")){// prof login
            for(Professor p: profList){
                if(p.getUsername().equals(username) && p.getPassword().equals(password)) {
                    prof = p;
                    break;
                }
            }
            // find the professor in database
            if(prof != null){
                profOperate(s);
            }else{
                System.out.println("The username/password is not correct");
                login(s, option);
            }
        }else if(option.equals("3")){// Admin login
            for(Admin a: adminList){
                if(a.getUsername().equals(username) && a.getPassword().equals(password)) {
                    admin = a;
                    break;
                }
            }
            // find the admin in database
            if(admin != null){
                adminOperate(s);
            }else{
                System.out.println("The username/password is not correct");
                login(s, option);
            }
        }
    }

    /**
     * Shows all of the course info for the given course list.
     * @param courseList to show
     */
    public void showCoursesInList(List<Course> courseList){
        for(Course course: courseList){
            System.out.println(course.toString());
        }
    }

    /**
     * Queries a course by the given cid.
     * @param cid the given course ID
     * @return if found, the course, otherwise null
     */
    public Course queryCourse(String cid){
        for(Course course: this.courseList){
            if(course.getId().equals(cid)) return course;
        }
        return null;
    }

    /**
     * Provides operations for a professor.
     * @param scanner for user input
     */
    public void profOperate(Scanner scanner){
        // show the menu
        profMenu();

        String option = scanner.next();
        if(option.equals("1")){// view all courses the professor taught
            prof.viewUserCourses(prof.getCourseList());
            System.out.println();
        }else if(option.equals("2")){// view students list for a given course
            while(true){
                boolean find = false;
                prof.viewUserCourses(prof.getCourseList());
                System.out.println();
                System.out.println("Please enter the course ID, eg. 'CIS519.");
                System.out.println("Or type 'q' to quit.");
                String queryCourseID = scanner.next();
                if(queryCourseID.equals("q")) break;
                for(Course course: courseList){
                    if(course.getId().equals(queryCourseID)){
                        System.out.println("Students in your course " + course.getId()  + " " + course.getCourseName() + ": ");
                        course.showStudentList();
                        System.out.println();
                        find = true;
                        break;
                    }
                }
                if(find)break;
                else{
                    System.out.println("The course ID you entered is not in your course list.");
                    System.out.println();
                }
            }
        }else if(option.equals("3")){// return to the previous menu
            mainMenu();
            option = scanner.next();
            login(scanner, option);
            return;
        }
        profOperate(scanner);
    }

    /**
     * Provides operations for a student.
     * @param scanner for user input
     */
    public void stuOperate(Scanner scanner){
        // show the menu
        stuMenu();
        String option = scanner.next();

        if(option.equals("1")){ // view all the courses
            showCoursesInList(courseList);
            System.out.println();
        }else if(option.equals("2")){ // add course
            showCoursesInList(courseList);
            System.out.println();
            while(true){
                System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'.");
                System.out.println("Or enter 'q' to return to the previous menu.");
                String selectCourseID = scanner.next();
                if(selectCourseID.equals("q")) break;
                Course c = queryCourse(selectCourseID);
                if(c == null){
                    System.out.println("The course ID you entered does not exist.");
                    continue;
                }
                // check if the course available
                if(!c.check_availability()){
                    System.out.println("The course you selected is not available.");
                    continue;
                }
                // check if already selected or conflict with other selected courses
                boolean hasCourse = false;
                for(Course selectedC: student.getCourseList()){
                    if(selectedC.getId().equals(selectCourseID)){
                        System.out.println("The course you selected is already in your list");
                        hasCourse = true;
                        break;
                    }
                }
                if(hasCourse) continue;

                // add course unsuccessful(time conflict), ask again
                if(!student.addClass(c)){
                    continue;
                }else{
                    System.out.println("Course added successfully");
                    System.out.println();
                }
            }

        }else if(option.equals("3")){ // view selected courses
            System.out.println();
            System.out.println("The courses in your list:");
            showCoursesInList(student.getCourseList());
            System.out.println();
        }else if(option.equals("4")){ //drop a course
            while(true){
                System.out.println();
                System.out.println("The courses in your list:");
                showCoursesInList(student.getCourseList());
                System.out.println();
                System.out.println("Please enter the ID of the course which you want to drop, eg. 'CIT591'.");
                System.out.println("Or enter 'q' to return to the previous menu");
                String dropCourse = scanner.next();
                if(dropCourse.equals("q")) break;
                Course course = queryCourse(dropCourse);
                if(!student.dropClass(course)) continue;
                else System.out.println("Course dropped successfully");
            }
        }else if(option.equals("5")){ // view grades
            student.viewGrades();
            System.out.println();
        }else if(option.equals("6")){ // return to previous menu
            mainMenu();
            option = scanner.next();
            login(scanner, option);
            return;
        }
        stuOperate(scanner);
    }

    /**
     * Provides operations for an admin.
     * @param scanner for user input
     */
    public void adminOperate(Scanner scanner){
        // show menu
        adminMenu();
        String option = scanner.next();

        if(option.equals("1")){ // view all courses
            System.out.println(courseList.size() + " courses in the system");
            showCoursesInList(courseList);
            System.out.println();
        }else if(option.equals("2")){ // add new courses
            admin.addNewCourse(courseList, profList, userNameSet, scanner);
        }else if(option.equals("3")){ // delete courses
            System.out.println(courseList.size() + " courses in the system");
            showCoursesInList(courseList);
            System.out.println();
            admin.deleteCourse(courseList, scanner);
        }else if(option.equals("4")){ // add profs
            admin.addNewProf(profList, userNameSet, scanner);
        }else if(option.equals("5")){ // delete profs
            System.out.println(profList.size() + " professors in the system");
            for(Professor p: profList){
                System.out.println(p.toString());
            }
            System.out.println();
            admin.deleteProf(profList, userNameSet, scanner);
        }else if(option.equals("6")){ // add students
            admin.addNewStudent(studentList, courseList, userNameSet, scanner);
        }else if(option.equals("7")){ // delete students
            System.out.println(studentList.size() + " students in the system");
            for(Student s: studentList){
                System.out.println(s.toString());
            }
            System.out.println();
            admin.deleteStudent(studentList, userNameSet, scanner);
        }else if(option.equals("8")){ // return to previous menu
            mainMenu();
            option = scanner.next();
            login(scanner, option);
            return;
        }
        adminOperate(scanner);
    }

    
    public static void main(String[] args) {
    	//create controller
    	Controller ctr = new Controller();
    	
    	//show main menu
        ctr.mainMenu();
        
        //create scanner
        Scanner scanner = new Scanner(System.in);
        
        //prompt user to login
        ctr.login(scanner, scanner.next());

    }

}
