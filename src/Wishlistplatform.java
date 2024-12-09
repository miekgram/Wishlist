import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Wishlistplatform {
private User user;
private TextUI textUI;
private Statement stmt;
private Connection conn;

public void startmenu(){
    // textUI.displayMsg("velkommen til ønskeskyen");
    //Kaldes via switchcase:
    //login()
    //register user

}
    public void login(){
    /*
    brugeren input = textUI.promptText
     String sql = "SELECT * FROM User"; //Skal være wishes
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String username = rs.getString("name")); //Kolonnenavn "name"
                hvis username er lige med (=) equalsTo(input),
                så skal brugeren skrive password (hvis fejl, prøv igen)
                String password = rs.getString("password")); //Kolonnenavn "password"
                hvis password er lige med (=) equalsTo(input), (hvis fejl, prøv igen)
                så kaldes homemenu
            }

        } catch (SQLException e) {
            e.getMessage();
        }
     */
    }
    public void registerUser(){
       // String userName = textUI.promptText("Write a username: ");
       // String password = textUI.promptText("Write a password: ");

        //this.currentUser = new User(userName, password);
        //fileIO.saveData(this.userDataPath,userName, password);

        //Homemenu kaldes
    }
    public void homemenu (){
    //have en textui.displayhomemenu
        //lave et switch case hvor man knumerisk kan se forskellige nedenstående muligheder
        //fx
       /* int choice = textUI.promptNumeric( """
                Please choose an option:
                1. create wishlist
                2. see wishlist
                3. inspiration
                4. see other wishlist
                """);
        switch (choice){
            case 1 : createWishlist(user, " "); break;
            case 2 : seeWishlist(user); break;
            case 3 : inspiration(user);break;
            case 4 : seeOtherWishlists(user);break;
            case 5 : {
                textUI.displayMsg("leaving homemenu");
                startmenu = new Startmenu();

            }
            default : textUI.displayMsg("invalid choice, try again");

        }*/
    }
    public void createWishlist(){
    //textui.displaymsg "name your wishlist"
        //String wishlistName = textUI.getStringInput();
        String name = textUI.promptText("Name your new wishlist: ");
        String event = textUI.promptText("What is it for? (Christmas, Birthday, Wedding...?):");
        String sql = "INSERT INTO Wishlists (Name, Event)";
        sql += "VALUES ("+name+", "+event+");";

//        String createWishlist = "CREATE TABLE " + name +"""(      Dette bruges hvis man opretter en ny tabel ved hver wishlist
//                         nameOfProducts TEXT,
//                         prices INTEGER,
//                         shops TEXT,
//                         descriptions TEXT,
//                         links TEXT);""";

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            //  stmt.executeQuery(createWishlist);

        } catch (SQLException e) {
            e.getMessage();
        }

    }
    public void viewWishlist(){
        //TODO: Vi skal have en metode, hvor de kan vælge hvilken ønskeliste de vil se, her


        String sql = "SELECT * FROM Wishes"; //Skal være wishes
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                textUI.displayMsg(rs.getString("itemName")); //Skal være title
            }

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void inspiration(){
    /* String choice = textUI.prompttext( """
                Please choose an option:
                1. bolig
                2. personligpleje
                3. mode
                4. børn
                5. hobby
                """);
        switch (choice){
            case 1 : database
            case 2 : database
            case 3 : database
            case 4 : database
            case 5 : database{
                textUI.displayMsg("leaving homemenu");
                startmenu = new homemenu();

            }
            default : textUI.displayMsg("invalid choice, try again");

        }*/
    }
    public void seeOtherWishlists() {
        //textUI.displayList(user.wishlist,"Here is others wishlist, would you like to reserve a wish: " );

        // reserve();

    }
    public void reserve(){
    //Tjek om produkt er reserveret, hvis ikke så skal databasens reserveret-kolonne ændres til true
    //boolean (true/false)

    }

    public void removeReservation(){
        //Tjek om produkt er reserveret, hvis det er, så skal databasens reserveret-kolonne ændres til false
    }

}