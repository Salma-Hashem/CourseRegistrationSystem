import java.util.ArrayList;
import java.io.Serializable;
import java.util.Scanner;

public class Course implements Serializable {
    private String courseName;
    private String courseID;
    private int maxStudents;
    private int currentStudents;
    private ArrayList<Student> studentList;
    private String instructor;
    private int sectionNumber;
    private String courseLocation;

    public Course(String _courseName, String _courseID, int _maxStudents, int _currentStudents, ArrayList<Student> _studentList, String _instructor, int _sectionNumber, String _courseLocation) {
        courseName = _courseName;
        courseID = _courseID;
        maxStudents = _maxStudents;
        currentStudents = _currentStudents;
        studentList = _studentList;
        instructor = _instructor;
        sectionNumber = _sectionNumber;
        courseLocation = _courseLocation;
    }

    public String getCourseName() { return courseName; }
    public String getCourseID() { return courseID; }
    public int getMaxStudents() { return maxStudents; }
    public int getCurrentStudents() { return currentStudents; }
    public ArrayList<Student> getStudentList() { return studentList; }
    public String getInstructor() { return instructor; }
    public int getSectionNumber() { return sectionNumber; }
    public String getCourseLocation() { return courseLocation; }

    public String setCourseName(String _courseName) {
        String tmp = courseName;
        courseName = _courseName;
        return tmp;
    }

    public String setCourseID(String _courseID) {
        String tmp = courseID;
        courseID = _courseID;
        return tmp;
    }

    public int setMaxStudents(int _maxStudents) {
        int tmp = maxStudents;
        maxStudents = _maxStudents;
        return tmp;
    }

    public int setCurrentStudents(int _currentStudents) {
        int tmp = currentStudents;
        currentStudents = _currentStudents;
        return tmp;
    }
    
    public ArrayList<Student> setStudentList(ArrayList<Student> _studentList) {
        ArrayList<Student> tmp = studentList;
        studentList = _studentList;
        return tmp;
    }

    public String setInstructor(String _instructor) {
        String tmp = instructor;
        instructor = _instructor;
        return tmp;
    }

    public int setSectionNumber(int _sectionNumber) {
        int tmp = sectionNumber;
        sectionNumber = _sectionNumber;
        return tmp;
    }

    public String setCourseLocation(String _courseLocation) {
        String tmp = courseLocation;
        courseLocation = _courseLocation;
        return tmp;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public Student removeStudent(Student student) {
        if (studentList.remove(student))
            return student;
        else
            return null;
    }

    public static Course createCourse() {
        System.out.println("Enter the following details of the course:");
        Scanner courseScanner = new Scanner(System.in);

        //Name of the course
        System.out.print("Course name: ");
        String courseName = courseScanner.nextLine();

        //CourseID
        System.out.print("Course ID: ");
        String courseID = courseScanner.nextLine();

        //max # of students
        System.out.print("Max Students (integer): ");
        boolean go = true;
        int maxStudents = 0;
        while (go) {
            try {
                maxStudents = Integer.parseInt(courseScanner.nextLine());
                go = false;
            } catch (Exception e) {
                System.out.println("Invalid input! Try again");
            }
        }

        //current # of students
        System.out.print("Current students (integer): ");
        go = true;
        int currentStudents = 0;
        while (go) {
            try {
                currentStudents = Integer.parseInt(courseScanner.nextLine());
                go = false;
            } catch (Exception e) {
                System.out.println("Invalid input! Try again");
            }
        }

        //ArrayList of studwnts is not added now
        ArrayList<Student> studentList = new ArrayList<Student>();

        //Instructor of the course
        System.out.print("Instructor: ");
        String instructor = courseScanner.nextLine();

        //Section number of the course
        System.out.print("Section number: ");
        int sectionNumber = 0;
        go = true;
        while(go) {
            try {
                sectionNumber = Integer.parseInt(courseScanner.nextLine());
                go = false;
            } catch(Exception e) {
                System.out.println("Invalid input! Try again");
            }
        }

        //location of the course
        System.out.print("Course Location: ");
        String courseLocation = courseScanner.nextLine();

        //create the new course with the info provided
        Course newCourse = new Course(courseName, courseID, maxStudents, currentStudents, studentList, instructor, sectionNumber, courseLocation);
        System.out.println("Course created!");
        return newCourse;
    }
}
