import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    //Helper method to serialize any ArrayList to a file
    public static void serializeToFile(ArrayList<?> list, String filename) {
            try {
                //Serializing the .csv file
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(list);
                oos.close();
                fos.close(); 
            } catch (IOException io) {
                System.out.println("IO error caught, details below.");
                io.printStackTrace();
            } catch (Exception e) {
                System.out.println("Some other exception caught, details below");
                e.printStackTrace();
            }

    }

    //Helper method to deserialize any bytestream to an ArrayList
    public static ArrayList<?> deserializeFromFile(String filename) {
            try {
                //Open the bytestream file
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);

                //set the courses variable to it
                ArrayList<?> list = (ArrayList<?>) ois.readObject();
                
                ois.close();
                fis.close();

                return list;
            } catch (IOException io) {
                System.out.println("IO error caught, details below.");
                io.printStackTrace();
                return null;
            } catch (Exception e) {
                System.out.println("Some other exception caught, details below");
                e.printStackTrace();
                return null;
            }
    }

    public static void main(String[] args) {
        //Create the ArrayList of necessary objects
        ArrayList<Course> courses = new ArrayList<Course>(); 
        ArrayList<Admin> admins = new ArrayList<Admin>();
        ArrayList<Student> students = new ArrayList<Student>();
        
        File test = new File("courses");
        //Open the MyUniversityCourses.csv file and serialize it, if it doesn't already exist
        if (!test.exists()) {
            //System.out.println("courses bytestream does not exists, creating it now");
            try {
                //Create the BufferedReader object that contains the .csv file
                File file = new File("MyUniversityCourses.csv");
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                //Skip the first line that has info about the .csv file.
                String line = "";
                br.readLine();

                //Loop through each line and add it to the ArrayList of courses
                while ((line = br.readLine()) != null ) {
                    String[] course = line.split(",");
                    Course csvCourse = new Course(course[0], course[1], Integer.parseInt(course[2]), Integer.parseInt(course[3]), null, course[5], Integer.parseInt(course[6]), course[7]);
                    courses.add(csvCourse);
                }
            } catch (IOException io) {
                System.out.println("Issue reading/opening the file");
            } catch (Exception e) {
                System.out.println("Some other issue arose");
            }
            //Call the serialize courses method
            serializeToFile(courses, "courses");

        } else {
            //System.out.println("courses file exists, attempting deserialization");
            //courses file exists, so simply deserialize it
            courses = (ArrayList<Course>)deserializeFromFile("courses");
        }
        //Check if the bytestream file exists for admins
        File tmp = new File("admins");
        if (!tmp.exists()) {
            //System.out.println("admins file does not exist, creating it now");
            //There is only admin, will add manually
            Admin admin = new Admin("Admin", "Admin001", "Anasse", "Bari");
            admins.add(admin);

            serializeToFile(admins, "admins");
        } else {
            //System.out.println("admins file does exist, will deserialize it");
            //File exists, so we deserialize it
            admins = (ArrayList<Admin>) deserializeFromFile("admins");
        }

        //check if the bytestream file exists for students
        File tmp_1 = new File("students");
        if (!tmp_1.exists()) {
            //if it doesn't exist, create the dummy account for students and add it to the bytestream
            Student student = new Student("johnsmith", "password", "John", "Smith", 1);
            students.add(student);
            serializeToFile(students, "students");
        } else {
            students = (ArrayList<Student>) deserializeFromFile("students");
        }

        //Greet the user and prompt for input
        System.out.println("Greetings and Welcome to the Course Registration System (CRS)");
        System.out.println("1, Admin\n2. Student");

        //Loop until valid input is detected.
        boolean go = true;
        int selection = 0;
        while(go) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            try {
                selection = Integer.parseInt(userInput);
                go = !(selection > 0 && selection <= 2);
            } catch (Exception e) {
                selection = 0;
            }
        }

        if (selection == 1) {
            //Set up permaloop until proper credentials are entered
            go = true;
            while(go) {
                System.out.println("Enter your username");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                if (userInput.equals(admins.get(0).getUsername())) {
                    System.out.println("Enter your password");
                    userInput = scanner.nextLine();
                    if (userInput.equals(admins.get(0).getPassword()))
                        go = false;
                    else
                        System.out.println("Invalid password, try again");
                } else {
                    System.out.println("Invalid username, try again");
                }
            }
            System.out.println("Logged in!");

            //Printing out the course management options
            go = true;
            int adminChoice = 0;
            while(go) {
                System.out.println("1. Course Management");
                System.out.println("2. Reports");
                Scanner scanner = new Scanner(System.in);
                try {
                    adminChoice = Integer.parseInt(scanner.nextLine());
                    go = false;
                    if (adminChoice > 2 || adminChoice < 1) {
                        go = true;
                        System.out.println("Invalid input, try again!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input, try again!");
                }
            }
            
            if (adminChoice == 1) {
                adminChoice = 0;
                go = true;
                while(go) {
                    System.out.println("Welcome Admin!");
                    System.out.println("1. Create a course");
                    System.out.println("2. Delete a course");
                    System.out.println("3. Edit a course");
                    System.out.println("4. Display information for a given course");
                    System.out.println("5. Register a student");
                    System.out.println("6. Exit");
                    Scanner scanner = new Scanner(System.in);
                    try {
                        adminChoice = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input, try again!");
                    }

                    if (adminChoice == 1)  {    //Creating a course
                        Course newCourse = Course.createCourse();
                        courses.add(newCourse);
                        serializeToFile(courses, "courses");
                    } else if (adminChoice == 2) {   //Deleting a course
                        System.out.println("Enter the course ID of the course you would like to delete");
                        for (Course course : courses) {
                            System.out.println("Course name: " + course.getCourseName() + "\tCourse ID: " + course.getCourseID());
                        }

                        Course removedCourse = null;
                        while(true) {
                            String courseSelected = scanner.nextLine();
                            for (Course course : courses) {
                                if (course.getCourseID().equals(courseSelected)) {
                                    removedCourse = course;
                                    break;
                                }
                            }
                            if (removedCourse == null) {
                                System.out.println("Invalid course selected, try again!");
                            } else {
                                System.out.println("Removed " + removedCourse.getCourseName() + " from course list");
                                break;
                            }
                        }
                        serializeToFile(courses, "courses");
                    } else if (adminChoice == 3) {   //Editing a course
                        System.out.println("Choose a course from the list of courses (by course ID)");
                        for (Course course : courses) {
                            System.out.println("Course Name: " + course.getCourseName() + "\tCourseID: " + course.getCourseID());
                        }

                        Course courseRequested = null;
                        while(true) {
                            String selectedCourse = scanner.nextLine();
                            for (Course course : courses) {
                                if (course.getCourseID().equals(selectedCourse)) {
                                    courseRequested = course;
                                    courses.remove(course);
                                    break;
                                }
                            }
                            if (courseRequested == null) 
                                System.out.println("Course not found, try again!");
                            else
                                break;
                        }

                        System.out.println("Course " + courseRequested.getCourseName() + " selected.");
                        System.out.println("Which of the following would you like to edit about the course?");
                        System.out.println("1. Max number of students");
                        System.out.println("2. Current number of students");
                        System.out.println("3. Instructor");
                        System.out.println("4. Section Number");
                        System.out.println("5. Location");
                        System.out.println("6. Exit");

                        go = true;
                        while(go) {
                            try {
                                selection = Integer.parseInt(scanner.nextLine());
                                go = false;
                                break;
                            } catch (Exception e){
                                System.out.println("Invalid input detected! Try again!");
                                go = true;
                            }
                        }
                        if (selection == 1) {
                            System.out.println("Max number of students: " + courseRequested.getMaxStudents());
                            System.out.println("Enter an integer to replace the above value");
                            go = true;
                            while(go) {
                                try {
                                    courseRequested.setMaxStudents(Integer.parseInt(scanner.nextLine()));
                                    go = false;
                                } catch (Exception e) {
                                    System.out.println("Invalid input, try again!");
                                }
                            }
                            courses.add(courseRequested);
                            serializeToFile(courses, "courses");
                            System.out.println("Changed the max number of students!");
                            go = true;
                        } else if (selection == 2) {
                            System.out.println("Current number of students: " + courseRequested.getCurrentStudents());
                            System.out.println("Enter an integer to replace the above value");
                            go = true;
                            while(go) {
                                try {
                                    courseRequested.setCurrentStudents(Integer.parseInt(scanner.nextLine()));
                                    go = false;
                                } catch (Exception e) {
                                    System.out.println("Invalid input, try again!");
                                }
                            }
                            courses.add(courseRequested);
                            serializeToFile(courses, "courses");
                            System.out.println("Changed the current number of students!");
                            go = true;
                        } else if (selection == 3) {
                            System.out.println("Current Instructor: " + courseRequested.getInstructor());

                            System.out.println("Enter a new instructor");
                            courseRequested.setInstructor(scanner.nextLine());

                            courses.add(courseRequested);
                            serializeToFile(courses, "courses");

                            System.out.println("Changed the instructor!");
                            go = true;
                        } else if (selection == 4) {
                            System.out.println("Current Section Number: " + courseRequested.getInstructor());
                            System.out.println("Enter an integer to replace the above value");

                            go = true;
                            while(go) {
                                try {
                                    courseRequested.setSectionNumber(Integer.parseInt(scanner.nextLine()));
                                    go = false;
                                } catch (Exception e) {
                                    System.out.println("Invalid input, try again!");
                                }
                            }

                            courses.add(courseRequested);
                            serializeToFile(courses, "courses");

                            System.out.println("Changed the section number!");
                            go = true;
                        } else if (selection == 5) {
                            System.out.println("Current Location: " + courseRequested.getCourseLocation());
                            System.out.println("Enter a new location");

                            courseRequested.setCourseLocation(scanner.nextLine());
                            
                            courses.add(courseRequested);
                            serializeToFile(courses,"courses");

                            System.out.println("Changed the location!");
                            go = true;
                        } else if (selection == 6) {
                            System.out.println("Returning to main screen...");
                            go = true;
                        }
                    } else if (adminChoice == 4) {   //Displaying info for course
                        System.out.println("Display information for a given course selected (use the Course ID)");
                        for (Course course : courses) {
                            System.out.println("Course Name: " + course.getCourseName() + " CourseID: " + course.getCourseID());
                        }

                        Course courseRequested = null;
                        while(true) {
                            String selectedCourse = scanner.nextLine();
                            for (Course course : courses) {
                                if (course.getCourseID().equals(selectedCourse)) {
                                    courseRequested = course;
                                    System.out.println("Course information for " + course.getCourseName());
                                    System.out.println("Section Number: " + course.getSectionNumber());
                                    System.out.println("Max # of Students: " + course.getMaxStudents());
                                    System.out.println("Current # of Students: " + course.getCurrentStudents());
                                    System.out.println("List of Students:");
                                    if (course.getStudentList() != null) {
                                        for(Student student : course.getStudentList()) {
                                            System.out.println(student.getFirstName() + " " + student.getLastName()); 
                                        }
                                    }
                                    System.out.println("Current Instrutor: " + course.getInstructor());
                                    System.out.println("Course Location: " + course.getCourseLocation());
                                    break;
                                }
                            }
                            if (courseRequested == null) {
                               System.out.println("Course not found, try again");
                            } else {
                               break;
                            }
                        }
                    } else if (adminChoice == 5) {   //Register a student
                        System.out.println("Register a student selected");
                        students.add(Student.registerStudent());
                        serializeToFile(students, "students");
                    } else if (adminChoice == 6) {   //Exit
                        System.out.println("Have a good day!");
                        go = false;
                        break;
                    } else {
                        System.out.println("Number out of range, try again!");
                        go = true;
                    }
                }
            } else if (adminChoice == 2) {
                Scanner scanner = new Scanner(System.in);
                int choice = -1;
                go = true;

                while (go) {
                    System.out.println("1. View all courses");
                    System.out.println("2. View all courses that are FULL");
                    System.out.println("3. Write to a file the list of course that are full");
                    System.out.println("4. View the names of the students that are registered in a specific course");
                    System.out.println("5. View the list of courses that a given student is registered in");
                    System.out.println("6. Sort the courses based on the current number of students registered");
                    System.out.println("7. Exit");
                    
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                                                                                               
                    } catch (Exception e) {
                        System.out.println("Invalid input! try again!");
                    }

                    if (choice == 1) {
                        for (Course course : courses) {
                            System.out.println("==========================================");
                            System.out.println("Course Name: " + course.getCourseName());
                            System.out.println("Course ID: " + course.getCourseID());
                            System.out.println("Current Number of Students: " + course.getCurrentStudents());
                            System.out.println("List of Students: ");
                            ArrayList<Student> studentList = course.getStudentList();
                            if (studentList != null) {
                                for (Student student : studentList)
                                    System.out.println(student.getFirstName() + " " + student.getLastName() + " ID: " + student.getStudentID());
                            }
                            System.out.println("Max Number of Students: " + course.getMaxStudents());
                        }
                    } else if (choice == 2) {
                        for(Course course : courses) {
                            if (course.getCurrentStudents() == course.getMaxStudents()) {
                                System.out.println("=====================================");
                                System.out.println("Course Name: " + course.getCourseName());
                                System.out.println("Course ID: " + course.getCourseID());
                                System.out.println("Current students / Max Students: " + course.getCurrentStudents() + " / " + course.getMaxStudents());
                            }
                        }
                    } else if (choice == 3) {
                        try {
                            File fullCourses = new File("full_courses.txt");
                            if(!fullCourses.exists())
                                fullCourses.createNewFile();
                            BufferedWriter bw = new BufferedWriter(new FileWriter(fullCourses.getAbsoluteFile()));
                            String listOfCourses = "";
                            for (Course course : courses) {
                                if(course.getCurrentStudents() == course.getMaxStudents()) {
                                    listOfCourses += course.getCourseName() + " Capacity: " + course.getCurrentStudents() + " / " + course.getMaxStudents() + "\n";
                                }
                            }
                            bw.write(listOfCourses);
                            bw.close();
                            System.out.println("Wrote full courses to file full_courses.txt");
                        } catch (IOException io) {
                            System.out.println("IOException occured, details below");
                            io.printStackTrace();
                        } catch (Exception e) {
                            System.out.println("Some other exception occured, details below");
                            e.printStackTrace();
                        }
                    } else if (choice == 4) {
                        System.out.println("Select a course by Course ID");
                        for (Course course : courses) {
                            System.out.println("Course Name: " + course.getCourseName() + "\nCourseID: " + course.getCourseID());
                            System.out.println();
                        }

                        Course courseRequested = null;
                        while(true) {
                            String selectedCourse = scanner.nextLine();
                            for (Course course : courses) {
                                if (course.getCourseID().equals(selectedCourse)) {
                                    courseRequested = course;
                                    if(courseRequested.getStudentList() != null) {
                                        ArrayList<Student> requestedStudents = course.getStudentList();
                                        for (Student student : requestedStudents) {
                                            System.out.println(student.getFirstName() + " " + student.getLastName());
                                        }
                                    } else {
                                        System.out.println("No Students enrolled in this course!");
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (courseRequested == null)
                                System.out.println("Course not found, try again!");
                            else
                                break;
                        }
                    } else if (choice == 5) {
                        System.out.println("All Students:");
                        for (Student student : students)
                            System.out.println(student.getFirstName() + " " + student.getLastName());
                        System.out.println("Enter the First and Last name of the student to see their registered Courses");

                        Student studentRequested = null;
                        while(true) {
                            String selectedStudent = scanner.nextLine();
                            for(Student student : students) {
                                if(selectedStudent.equals(student.getFirstName() + " " + student.getLastName())) {
                                    studentRequested = student;
                                    ArrayList<Course> registeredCourses = student.getRegisteredCourses();
                                    for (Course course : registeredCourses) {
                                        System.out.println("Course Name: " + course.getCourseName());
                                    }
                                    break;
                                }
                            }
                            if (studentRequested == null)
                                System.out.println("Student not found, try again!");
                            else
                                break;
                        }
                    } else if (choice == 6) {
                        System.out.println("Sorting the courses....");
                        //Will be using bubble sort for this
                        int size = courses.size();
                        for(int i = 0; i < size - 1; i++) {
                            for(int j = 0; j < size - i - 1; j++) {
                                if(courses.get(j).getCurrentStudents() > courses.get(j + 1).getCurrentStudents()) {
                                    Collections.swap(courses, j, j + 1);
                                }
                            }
                        }
                        for (Course course: courses) {
                            System.out.println("=================================");
                            System.out.println("Course Name: " + course.getCourseName() + "\nCurrent # of Students: " + course.getCurrentStudents());
                        }
                    } else if (choice == 7) {
                        System.out.println("Goodbye!");
                        go = false;
                    } else {
                        System.out.println("Invalid input! Try again");
                    }
                }
            }         
        } else {
            Student user = null;
            Scanner scanner = new Scanner(System.in);

            while(true) {
                System.out.println("Enter your username");
                String userInput = scanner.nextLine();
                for (Student student : students) {
                    if (student.getUsername().equals(userInput)) {
                        user = student;
                        break;
                    }
                }
                System.out.println("Enter your password");
                userInput = scanner.nextLine();
                if(user != null) {
                    if (userInput.equals(user.getPassword()))
                        break;
                    else
                        System.out.println("Invalid password, try again");
                }
            }            
            System.out.println("Logged in!");
            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
            if (user.getRegisteredCourses() == null)
                user.setRegisteredCourses(new ArrayList<Course>());

            while(true) {
                System.out.println("Course Management");
                System.out.println("1. View all courses");
                System.out.println("2. View all courses that are NOT full");
                System.out.println("3. Register in a course");
                System.out.println("4. Withdraw from a course");
                System.out.println("5. View all courses the current student is registered in");
                System.out.println("6. Exit");

                int choice = -1;
                while(true) {
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid choice! Try again");
                    }
                    if (choice != -1)
                        break;
                }
                if (choice == 1) {
                    for (Course course : courses) 
                        System.out.println("Course Name: " + course.getCourseName());
                } else if (choice == 2) {
                    for(Course course : courses) {
                        if (course.getCurrentStudents() < course.getMaxStudents())
                            System.out.println("Course Name: " + course.getCourseName());
                    }
                } else if (choice == 3) {
                    System.out.println("Here is a list of available courses that aren't full");
                    ArrayList<Course> availableCourses = new ArrayList<Course>();
                    for(Course course : courses) {
                        if (course.getCurrentStudents() < course.getMaxStudents()) {
                            System.out.println("Course Name: " + course.getCourseName());
                            System.out.println("Course ID: " + course.getCourseID());
                            System.out.println();
                            availableCourses.add(course);
                        }
                    }
                    go = true;
                    while(go) {
                        System.out.print("Enter the Course ID of the course you wish to enroll in: ");
                        String courseID = scanner.nextLine();
                        for (Course course : availableCourses) {
                            if (courseID.equals(course.getCourseID())) {
                                for(Course enrolledCourse : user.getRegisteredCourses()) {
                                    if (courseID.equals(enrolledCourse.getCourseID())) {
                                        System.out.println("Student already enrolled in this course!");
                                        break;
                                    }
                                }
                                course.setCurrentStudents(course.getCurrentStudents() + 1);
                                if (course.getStudentList() != null)
                                    course.getStudentList().add(user);
                                else
                                    course.setStudentList(new ArrayList<Student>());
                                user.addRegisteredCourse(course);
                                serializeToFile(courses, "courses");
                                serializeToFile(students, "students");
                                go = false;
                            }
                        }
                    }
                    } else if (choice == 4) {
                    System.out.println("Currently Enrolled Courses:");
                    if (user.getRegisteredCourses() != null && user.getRegisteredCourses().size() != 0) {
                        for (Course course : user.getRegisteredCourses()) {
                            System.out.println("Course Name: " + course.getCourseName());
                            System.out.println("Course ID: " + course.getCourseID());
                            System.out.println();
                        }
                        go = true;
                        while(go) {
                            System.out.println("Enter the Course ID of the course you would like to withdraw from");
                            String input = scanner.nextLine();
                            for (Course course : user.getRegisteredCourses()) {
                                if(input.equals(course.getCourseID())) {
                                    course.getStudentList().remove(user);
                                    course.setCurrentStudents(course.getCurrentStudents() - 1);
                                    user.getRegisteredCourses().remove(course);

                                    go = false;
                                    serializeToFile(courses, "courses");
                                    serializeToFile(students, "students");
                                    break;
                                }
                                if (go) {
                                    System.out.println("Invalid Course ID, try again!");
                                }
                            }                            
                        }
                    } else {
                        System.out.println("Not enrolled in any courses!");
                    }

                } else if (choice == 5) {
                    System.out.println("Currently Enrolled Courses:");
                    if (user.getRegisteredCourses() != null && user.getRegisteredCourses().size() != 0) {
                        for (Course course : user.getRegisteredCourses())
                            System.out.println("Course Name: " + course.getCourseName());
                    } else {
                        System.out.println("Not enrolled in any courses!");
                    }

                } else if (choice == 6) {
                    System.out.println("Bye bye!");
                    break;
                }
            }
        }
    }
}
