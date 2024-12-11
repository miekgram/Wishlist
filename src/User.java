public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private int userID;
    private String email;

    public User(String username, String password, String name, int age, int userID, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.userID = userID;
        this.email = email;
    }




   /* @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", name='" + name + '\'' +
               ", age=" + userID +
               ", email='" + email + '\'' +
               '}';
    }
    */

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
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.name = String.valueOf(userID);
    }

    }



