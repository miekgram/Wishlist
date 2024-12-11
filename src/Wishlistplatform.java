import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Wishlistplatform extends Category {
    private User currentUser;
    private TextUI textUI = new TextUI();
    private Statement stmt;
    private Connection conn;
    //private ArrayList<Wishlist> whishlistList = new ArrayList();
    private Wishlist wishlist = new Wishlist();

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
        switch (choice) {
            case 1:
                createWishlist();
                break;
            case 2:
                viewWishlist();
                break;
            case 3:
                inspiration();
                break;
            case 4:
                seeOtherWishlists();
                break;
            case 5: {
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
        String sql = "INSERT INTO Wishlists (title, ownerID, event) VALUES ";
        sql += "('" + name + "', '" + currentUser.getUserID() + "', '" + event + "');";

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            //  stmt.executeQuery(createWishlist);

        } catch (SQLException e) {
            e.getMessage();
        }
        homemenu();

    }

    public void viewWishlist() {
        try {
            textUI.displayMsg("Here are all your wishlist's");
            String sql = "SELECT * FROM Wishlists WHERE ownerID = " + currentUser.getUserID() + ";";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String title = rs.getString("title");
                int wishlistID = rs.getInt("WishlistID");
                textUI.displayMsg(" - " + title + ", ID: " + wishlistID);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        int choice = textUI.promptNumeric("Please type the ID of the wishlist you want to see: ");
        //TODO: Vi skal have en metode, hvor de kan vælge hvilken ønskeliste de vil se, her

        try {
            String sql = "SELECT * FROM Wishes WHERE WishlistID = " + choice; //TODO: = id af brugerens valgte ønskeliste
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                textUI.displayMsg(rs.getString("item"));
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        boolean edit = textUI.promptBinary("Would you like to edit this wishlist? y/n");
        if (edit == true) {
            editWishlist(choice);
        } else {
            homemenu();
        }

    }

    public void inspiration(){
        textUI.displayMsg("You are now in inspiration!");
        String sql = "";
        int choice = textUI.promptNumeric("""
                Please choose an option:
                1. Household
                2. Personalcare
                3. Fashion
                4. Children
                5. Hobby
                """);
        switch (choice) {
            case 1:
                houseHold(this.conn, this.textUI);
                break;
            case 2:
                personalCare(this.conn, this.textUI);
                break;
            case 3:
                fashion(this.conn, this.textUI);
                break;
            case 4:
                children(this.conn, this.textUI);
                break;
            case 5:
                hobby(this.conn, this.textUI);
                break;
            case 6: {
                textUI.displayMsg("leaving homemenu");


            }

            default:
                textUI.displayMsg("invalid choice, try again");
                homemenu();
        }
        homemenu();
    }
    public void seeOtherWishlists() {
        textUI.displayMsg("Here is others wishlist" );
        String choice = textUI.promptText("""
                What would you like to do?
                reserve a wish (type y)
                remove reservation (type r)
                """);
        switch (choice) {
            case "y":
                wishlist.reserve();
                break;
            case "r":
                wishlist.removeReservation();
                break;
            default:
                textUI.displayMsg("Wrong input, please try again");
                homemenu();
        }



    }
    public void editWishlist(int wishlistID) {

        int choice = textUI.promptNumeric( """
                Please choose an option:
                1. Add wish
                2. Remove wish
                3. Edit order of wishes""");
        switch(choice){
            case 1: addWish(wishlistID);
            case 2: removeWish(wishlistID);
            case 3: editOrder();
        }


    }


    public void addWish(int wishlistID) {
       /* if(!user.wishlist.contains(wish.getnameOfItem())){
            user.wishlist.add(wish.getnameOfItem());
            textUI.displayMsg(wish.getnameOfItem() + " has been added to your list.\n");

        }else{
            textUI.displayMsg(wish.getnameOfItem() + " is already in your list.\n");

    }*/
        String nameOfProduct = textUI.promptText("Type name of product: ");
        int price = Integer.parseInt(textUI.promptText("Type the price of the product:"));
        String store = textUI.promptText("In what store can u buy this product? ");
        String description = textUI.promptText("Description (size, color, etc.?): ");
        String category = textUI.promptText("What category matches this product? Hobby, Children, Clothes, Home, Beauty? ");
        String link = textUI.promptText("A link for a website: ");
        boolean reserved = false;

        //Tilføjer info til wishes
        String sql = "INSERT INTO wishes (nameOfProducts, prices, store, descriptions, links, Categories, WishlistID,reservated) VALUES";
        sql += "('"+nameOfProduct+"', "+price+", '"+store+"', '"+description+"', '"+ link+"', '"+category +"', "+ wishlistID+", '"+ reserved+"');";
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void removeWish(int wishlistID) {
        /*if(user.wishlist.contains(wish.getnameOfItem())){
            user.wishlist.remove(wish.getnameOfItem());
            textUI.displayMsg(wish.getnameOfItem() + " has been removed from your wishlist list.\n");

        }else{
            textUI.displayMsg(wish.getnameOfItem() + " is not on your list.\n");
        }*/
        String deleteWish = textUI.promptText("Write the exact name of the product you want to delete from your wishlist: ");
        String sql = "DELETE FROM wishes WHERE nameOfProducts = " + deleteWish + ";";
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }


    public void editOrder() {

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




}