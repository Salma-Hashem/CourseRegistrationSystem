import java.io.Serializable;

public class User implements Serializable {
   private String username;
   private String password;
   private String firstName;
   private String lastName;

   public User(String _username, String _password, String _firstName, String _lastName) {
       username = _username;
       password = _password;
       firstName = _firstName;
       lastName = _lastName;
   }

   public String getUsername() { return username; }
   public String getPassword() { return password; }
   public String getFirstName() { return firstName; }
   public String getLastName() { return lastName; }

   public String setUsername(String _username) {
       String tmp = username;
       username = _username;
       return tmp;
   }

   public String setPassword(String _password) {
       String tmp = password;
       password = _password;
       return tmp;
   }

   public String setFirstName(String _firstName) {
       String tmp = firstName;
       firstName = _firstName;
       return tmp;
   }

   public String setLastName(String _lastName) {
       String tmp = lastName;
       lastName = _lastName;
       return tmp;
   }

}
