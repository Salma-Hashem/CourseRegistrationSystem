import java.util.ArrayList;

public interface StudentInterface {
    public int getStudentID();
    public ArrayList<Course> getRegisteredCourses();

    public int setStudentID(int _studentID);
    public ArrayList<Course> setRegisteredCourses(ArrayList<Course> _registeredCourses);

    public void addRegisteredCourse(Course course);
}
