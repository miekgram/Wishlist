import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Wishlistplatform {
    private User currentUser;
    private TextUI textUI = new TextUI();
    private Statement stmt;
    private Connection conn;
    //private ArrayList<Wishlist> wishlistList = new ArrayList();
    private Wishlist wishlist = new Wishlist();


    public Wishlistplatform(Connection conn) {
        this.conn = conn;

    }


    public void startmenu() {
        textUI.displayMsg("Welcome to Mmm Wishes");
        String choice = textUI.promptText("""
                What would you like to do?
                Login (type l)
                Register new user (type r)
                """);

        switch (choice) {
            case "l":
                login();
                break;
            case "r":
                registerUser();
                break;
            default:
                textUI.displayMsg("Wrong input, please try again");
                startmenu();
                break;
        }
    }

    public void login() {
        typeUsername();
    }


    public void typeUsername() {
        String usernameInput = textUI.promptText("Please type your username: ");  //Bruger taster sit brugernavn
        String sql = "SELECT * FROM User";
        int row = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                row++;
                String username = rs.getString("username");               //Henter username kolonnen ind, række efter række
                if (username.equals(usernameInput)) {
                    typePassword(row);
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void typePassword(int targetrow) {
        String passwordInput = textUI.promptText("Please type your password: "); //Bruger taster sit password
        String sql = "SELECT * FROM User";
        int row = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                row++;
                if (row == targetrow) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");                 //Henter password kolonnen ind fra den række som username også er fundet på
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    int userID = rs.getInt("UserID");
                    String email = rs.getString("email");                //Henter password kolonnen ind fra den række som username også er fundet på
                    if (password.equals(passwordInput)) {
                        this.currentUser = new User(username, password, name, age, userID, email);//Tjekker om brugeren input matcher noget i password-kolonnen
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


    public void registerUser() {
        String userName = textUI.promptText("Write a username: ");
        String password = textUI.promptText("Write a password: ");
        String name = textUI.promptText("Please write your name:");
        int age = Integer.parseInt(textUI.promptText("Please enter your age:"));
        String email = textUI.promptText("Please enter your email: ");
        textUI.displayMsg("Fantastic, you are now ready to make wishes!");

        //Tilføjer det som brugeren taster ind til databasen (user-table)
        String sql = "INSERT INTO User (username, password, name, age, email) VALUES";
        sql += "('" + userName + "', '" + password + "', '" + name + "', " + age + ", '" + email + "');";

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.getSQLState();
        }

        String getUserID = "SELECT * FROM User";
        int userID = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserID);
            while (rs.next()) {
                String username = rs.getString("username");
                if (userName.equals(username)) {
                    userID = rs.getInt("UserID");
                }
                currentUser = new User(userName, password, name, age, userID, email);
                //System.out.println("A new user was created: "+currentUser.toString()); //Jespers hjælp
                homemenu();

            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void homemenu() {
        textUI.displayMsg("You are now in your homemenu!");

        int choice = textUI.promptNumeric("""
                Please choose an option:
                1. create a wishlist
                2. view your wishlist
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
            default:
                textUI.displayMsg("invalid choice, you are now in startmenu");
                startmenu();
                break;
        }
    }

    public void createWishlist() {
        String name = textUI.promptText("Name your new wishlist: ");
        String event = textUI.promptText("What is it for? (Christmas, Birthday, Wedding...?):");
        String sql = "INSERT INTO Wishlists (title, ownerID, event) VALUES ";
        sql += "('" + name + "', '" + currentUser.getUserID() + "', '" + event + "');";

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
        homemenu();

    }

    public void viewWishlist() {

        textUI.displayMsg("Here are all your wishlist's");
        String sql = "SELECT * FROM Wishlists WHERE ownerID = " + currentUser.getUserID() + ";";
        ResultSet rs = getResultSetBySQL(sql);
        displayWishlists(sql); //Viser alle brugeren egne ønskelister
        int choice = textUI.promptNumeric("Please type the ID of the wishlist you want to see: ");
        //Brugeren vælger wishlistID de vil se //Kunne være fedt, hvis man valgte det ud fra noget andet end ID
        String getWishes = "SELECT * FROM Wishes WHERE WishlistID = "+ choice;
        getWishesFromWishlist(getWishes);

        boolean edit = textUI.promptBinary("Would you like to edit in this wishlist? y/n");
        if (edit == true) {
            editWishlist(choice);
        } else {
            homemenu();
        }
    }

    public void inspiration() {
        textUI.displayMsg("You are now in inspiration!");
        String category = null;
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
                category = "Household";
                break;
            case 2:
                category = "Personalcare";
                break;
            case 3:
                category = "Fashion";
                break;
            case 4:
                category = "Children";
                break;
            case 5:
                category = "Hobby";
                break;
            default:
                textUI.displayMsg("invalid choice, try again");
                break;
        }
        String sql = "SELECT * FROM Wishes WHERE categories ='" + category + "';";
        ArrayList<Wish> wishes = getWishesFromWishlist(sql);
        textUI.displayListOfWishes(wishes, "Here is the list of " + category + " inspiration");
        homemenu();
    }

    public void seeOtherWishlists() {
        textUI.displayMsg("Here is others wishlist");
        String sql = "SELECT * FROM Wishlists WHERE NOT ownerID = " + currentUser.getUserID() + ";";
        displayWishlists(sql);  //displayer alle ønskelister, der ikke har samme ownerID som den pågældende bruger

        int wishListId = textUI.promptNumeric("Please type the ID of the wishlist you want to see: ");
        String getWishes = "SELECT * FROM Wishes WHERE WishlistID = " + wishListId;
        ArrayList<Wish> listOfWishes = getWishesFromWishlist(sql);
        textUI.displayListOfWishes(listOfWishes, "Here is all the wishes from the chosen wishlist");


        String choice = textUI.promptText("""
                What would you like to do?
                Reserve a wish (type y)
                Remove a reservation (type r)
                Go back to homemenu(type h)
                """);
        switch (choice) {
            case "y":
                reserve(listOfWishes, wishListId);
                break;
            case "r":
                removeReservation(listOfWishes, wishListId);
                break;
            case "h":
                homemenu();
                break;
            default:
                textUI.displayMsg("Wrong input, please try again");
                seeOtherWishlists();
        }
    }


    public void editWishlist(int wishlistID) {

        int choice = textUI.promptNumeric("""
                Please choose an option:
                1. Add wish
                2. Remove wish
                3. Edit order of wishes""");
        switch (choice) {
            case 1:
                addWish(wishlistID);
            case 2:
                removeWish(wishlistID);
            case 3:
                editOrder();
        }
    }


    public void addWish(int wishlistID) {
        String nameOfProduct = textUI.promptText("Type the name of the product: ");
        int price = Integer.parseInt(textUI.promptText("Type the price of the product:"));
        String store = textUI.promptText("In what store can u buy this product? ");
        String description = textUI.promptText("Description (size, color, etc.?): ");
        String category = textUI.promptText("What category matches this product? Hobby, Children, Fashion, Household, Personalcare? ");
        String link = textUI.promptText("A link for a website: ");
        boolean reserved = false;

        //Tilføjer info til wishes
        String sql = "INSERT INTO Wishes (item, prices, store, description, links, categories, WishlistID, reservated) VALUES";
        sql += "('" + nameOfProduct + "', " + price + ", '" + store + "', '" + description + "', '" + link + "', '" + category + "', " + wishlistID + ", '" + reserved + "');";
        String succes = "Wish added successfully!";
        String error = "Wish was not added to wishlist";
        SQLExecuteQuery(sql, succes, error);
        homemenu();
    }

    public void removeWish(int wishlistID) {
        String deleteWish = textUI.promptText("Write the exact name of the product you want to delete from your wishlist: ");
        String sql = "DELETE FROM Wishes WHERE item='" + deleteWish + "';";
        String succes = "Wish removed successfully!";
        String error = "Wish was not removed from your wishlist";
        SQLExecuteQuery(sql, succes, error);
        homemenu();
    }


    public void editOrder() {
        textUI.displayMsg("Not avaiable yet:)");
        homemenu();
    }

    public void reserve(ArrayList<Wish> listOfWishes, int wishListId) {

        textUI.displayMsg("Here is a list of all the wishes:");

        for (Wish wish : listOfWishes) {
            textUI.displayMsg(wish + " - Reservated: " + wish.getIsReservated()); //Printer wish toString og reserveret: true/false
        }
        String choice = textUI.promptText("choose a wish to reserve");
        reserveWishInDb(choice);
        textUI.displayMsg("You have reserved this wish! and will now be returning to homemenu");
        homemenu();
    }

    public void reserveWishInDb(String choice) {
        try {
            String sql = "UPDATE Wishes SET reservated = true WHERE item = '" + choice + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void removeReservation(ArrayList<Wish> listOfWishes, int wishListId) {
        textUI.displayMsg("Here is a list of all the wishes:");

        for (Wish wish : listOfWishes) {
            textUI.displayMsg(wish + " - Reservated: " + wish.getIsReservated()); //Printer wish toString og reserveret: true/false
        }
        String removeChoice = textUI.promptText("choose a wish to remove your reservation from");
        removeReservationInDb(removeChoice);
        textUI.displayMsg("You have removed your reservation! and will now be returning to homemenu");
        homemenu();
    }

    public void removeReservationInDb(String removeChoice) {
        try {
            String sql = "UPDATE Wishes SET reservated = false WHERE item = '" + removeChoice + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void displayWishlists(String sql) {
        ResultSet rs = getResultSetBySQL(sql);
        try {
            while (rs.next()) {
                String title = rs.getString("title");
                int wishlistID = rs.getInt("WishlistID");
                textUI.displayMsg("- " + title + ", ID: " + wishlistID);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public ArrayList<Wish> getWishesFromWishlist(String sql) {
        ArrayList<Wish> wishesFromWishlistID = new ArrayList<>();

            ResultSet rs = getResultSetBySQL(sql);
            try {
                while (rs.next()) {
                    String item = rs.getString("item");
                    String description = rs.getString("description");
                    int price = rs.getInt("prices");
                    String category = rs.getString("categories");
                    String url = rs.getString("links");
                    boolean isReserved = rs.getBoolean("reservated");
                    Wish wish = new Wish(item, description, price, category, url, isReserved);
                    wishesFromWishlistID.add(wish);
                    System.out.println(wish);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
            return wishesFromWishlistID;
    }


        public void SQLExecuteQuery (String SQLQuery, String succes, String errorMsg){
            try {
                String sql = SQLQuery;
                stmt = conn.createStatement();
                textUI.displayMsg(succes);
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                textUI.displayMsg(errorMsg);
                e.getMessage();
            }
        }

        public ResultSet getResultSetBySQL (String SQLQuery){
            ResultSet rs = null;
            try {
                String sql = SQLQuery;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

            } catch (SQLException e) {
                e.getMessage();
            }
            return rs;
        }

    }
