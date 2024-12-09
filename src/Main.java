import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlite:/Users/miegram/Documents/ICE/Wishlist/data/Database");
        }
        catch (SQLException e) {

            e.printStackTrace();
            System.exit(1);
        }
        Wishlistplatform wishlistplatform = new Wishlistplatform(con);
        wishlistplatform.startmenu();



    }
}