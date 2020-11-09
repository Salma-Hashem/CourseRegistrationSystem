import java.io.Serializable;

public class Admin extends User implements Serializable, AdminInterface {
// Admin constructor
   public Admin(String _username, String _password, String _firstName, String _lastName) {
       super(_username, _password, _firstName, _lastName);
   }

  
}
