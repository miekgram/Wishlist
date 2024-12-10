import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Wishlistplatform {
private User user;
private TextUI textUI = new TextUI();
private Statement stmt;
private Connection conn;

    public Wishlistplatform(Connection conn) {
        this.conn = conn;

    }

    public void startmenu(){
    textUI.displayMsg("Velkommen til ønskeskyen");
    String choice = textUI.promptText("""
            What would you like to do?
            Login (type l)
            Register new user (type r)
            """);
    //Kaldes via switchcase:
    //login()
    //register user

    switch(choice){
        case "l" : login(); break;
        case "r" : registerUser(); break;
        default : textUI.displayMsg("Wrong input, please try again");
            startmenu(); break;
    }
}
    public void login(){

            typeUsername();
        }


        public void typeUsername(){
            String usernameInput = textUI.promptText("Please type your username: ");  //Bruger taster sit brugernavn
            String sql = "SELECT * FROM User";
            int row = 0;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    row++;
                    String username = rs.getString("username");               //Henter username kolonnen ind, række efter række
                    if (username.equals(usernameInput)) {
                        typePassword(row);
                    }
                }
            } catch(SQLException e) {
                e.getMessage();
            }
        }

        public void typePassword(int targetrow){
            String passwordInput = textUI.promptText("Please type your password: "); //Bruger taster sit password
            String sql = "SELECT * FROM User";
            int row = 0;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    row++;
                    if (row == targetrow){
                        String password = rs.getString("password");                 //Henter password kolonnen ind fra den række som username også er fundet på
                        if(password.equals(passwordInput)){                                    //Tjekker om brugeren input matcher noget i password-kolonnen
                            homemenu();
                        } else {
                            textUI.displayMsg("Wrong input, try again. Type your password: ");
                            typePassword(targetrow);
                        }
                    }
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
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

    public void registerUser(){
        String userName = textUI.promptText("Write a username: ");
        String password = textUI.promptText("Write a password: ");
        String name = textUI.promptText("Please write your name:");
        int age = Integer.parseInt(textUI.promptText("Please enter your age:"));
        String email = textUI.promptText("Please enter your email: ");
        textUI.displayMsg("Fantastic, you are now ready to make wishes!");

        //Tilføjer det som brugeren taster ind til databasen (user-table)
        String sql = "INSERT INTO User (username, password, name, age, email) VALUES";
        sql += "('"+userName+"', '"+password+"', '"+name+"', "+age+", '"+email+"');";
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.getSQLState();
        }
        user = new User(userName, password, name, age, email);
        System.out.println("A new user was created: "+user.toString());

        homemenu();
    }
    public void homemenu (){
        textUI.displayMsg("You are now in homemenu!");

       int choice = textUI.promptNumeric( """
                Please choose an option:
                1. create wishlist
                2. view wishlist
                3. inspiration
                4. see other wishlist
                """);
        switch (choice){
            case 1 : createWishlist(); break;
            case 2 : viewWishlist(); break;
            case 3 : inspiration();break;
            case 4 : seeOtherWishlists();break;
            case 5 : {
                textUI.displayMsg("leaving homemenu");


            }
            default : textUI.displayMsg("invalid choice, try again");
                startmenu();
        }
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
        textUI.displayMsg("here is all your wishlist's");


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
        homemenu();
    }

    public void inspiration(){
        textUI.displayMsg("You are now in inspiration!");
    int choice = textUI.promptNumeric( """
                Please choose an option:
                1. Household
                2. Personalcare
                3. Fashion
                4. Children
                5. Hobby
                """);
        switch (choice){
            case 1 : break;
            case 2 : break;
            case 3 : break;
            case 4 : break;
            case 5 : {
                textUI.displayMsg("leaving homemenu");


            }
            default : textUI.displayMsg("invalid choice, try again");
                homemenu();
        }
    }
    public void seeOtherWishlists() {
        textUI.displayMsg("Here is others wishlist" );
        String choice = textUI.promptText("""
            What would you like to do?
            reserve a wish (type y)
            remove reservation (type r)
            """);


        switch(choice){
            case "y" : reserve(); break;
            case "r" : removeReservation(); break;
            default : textUI.displayMsg("Wrong input, please try again");
                homemenu();
        }



    }
    public void reserve(){
        textUI.displayMsg("You have reserved this wish!");

    //Tjek om produkt er reserveret, hvis ikke så skal databasens reserveret-kolonne ændres til true
    //boolean (true/false)

    }

    public void removeReservation(){
        textUI.displayMsg("You have removed your reservation from this wish!");
        //Tjek om produkt er reserveret, hvis det er, så skal databasens reserveret-kolonne ændres til false
    }

}