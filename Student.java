import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements Serializable, StudentInterface {
   private int studentID;
   private ArrayList<Course> registeredCourses;

   public Student(String _username, String _password, String _firstName, String _lastName, int _studentID) {
       super(_username, _password, _firstName, _lastName);
       studentID = _studentID;
   }

   public int getStudentID() { return studentID; }
   public ArrayList<Course> getRegisteredCourses() { return registeredCourses; }

   public int setStudentID(int _studentID) {
       int tmp = studentID;
       studentID = _studentID;
       return tmp;
   }

   public ArrayList<Course> setRegisteredCourses(ArrayList<Course> _registeredCourses) {
       ArrayList<Course> tmp = registeredCourses;
       registeredCourses = _registeredCourses;
       return tmp;
   }

   public void addRegisteredCourse(Course course) {
       registeredCourses.add(course);
   }

   public static Student registerStudent() {
       System.out.println("Enter the following details for the student:");
       Scanner studentScanner = new Scanner(System.in);

       //First and Last name of Student
       System.out.print("First Name: ");
       String _firstName = studentScanner.nextLine();
       System.out.print("Last Name: ");
       String _lastName = studentScanner.nextLine();

       //Student login information
       System.out.print("Student username: ");
       String _username = studentScanner.nextLine();
       System.out.print("Student password: ");
       String _password = studentScanner.nextLine();

       //Ask for Student ID (must be an integer)
       int _studentID = -1;
       while(true) {
           System.out.print("Student ID (please enter an integer): ");
           try {
               _studentID = Integer.parseInt(studentScanner.nextLine());               
           } catch (Exception e) {
               System.out.println("Invalid input! Try again");
           }
           if (!(_studentID == -1))
               break;
       }
       Student newStudent = new Student(_username, _password, _firstName, _lastName, _studentID);
       System.out.println("New Student " + newStudent.getFirstName() + " " + newStudent.getLastName() + " created!");

       return newStudent;
   }

   
}
